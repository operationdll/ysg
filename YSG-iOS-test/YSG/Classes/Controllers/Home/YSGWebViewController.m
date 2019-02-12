//
//  YSGWebViewController.m
//  YSG
//
//  Created by Apple on 2017/6/24.
//  Copyright © 2017年 Apple. All rights reserved.
//

#import "YSGWebViewController.h"
#import "XZConstans.h"
#import <AFNetworkReachabilityManager.h>
#import "WTNetWorkUtil.h"
#import <JavaScriptCore/JavaScriptCore.h>
#import "WTUtil.h"
#import "YSGGetSystemTimeRequest.h"
#import "YSGGetSystemTimeResponse.h"
#import "YSGGetLaunchImageRequest.h"
#import "YSGGetLaunchImageResponse.h"
#import "YXOpenMap.h"
#import "YXLocationManager.h"
#import "AppDelegate.h"
@protocol JSIneract <JSExport>
JSExportAs(request,
           - (NSString *)paramName:(NSDictionary *)paramDict
           );
- (void)openMap:(NSDictionary *)dict;
- (void)getLocation;
- (BOOL)getFirstLaunch;
- (void)saveJSInfo:(NSDictionary *)dict;
- (void)addUMTagAlias:(NSDictionary *)dict;
- (NSDictionary *)getJSInfo;
- (void)openSafariUrl:(NSString *)url;
- (void)removeAllTagAlias:(NSDictionary *)dict;
- (void)saveJSInfo:(NSDictionary *)dict;

@end

@interface YSGWebViewController ()<UIWebViewDelegate,JSIneract>
@property (weak, nonatomic) IBOutlet UIWebView *webView;
@property (nonatomic,strong) NSString  *publicTime;
@property (nonatomic,strong) NSMutableDictionary  *params;
@property (nonatomic,strong) NSDictionary *result;
@property (nonatomic,assign) long timeLong;
@property (nonatomic,strong) JSContext *context;
@property (nonatomic,assign) BOOL  isLoading;

@end

@implementation YSGWebViewController
{
    YSGWebViewController *weakSelf;
}
- (void)viewDidLoad {
    [super viewDidLoad];
    [[UIApplication sharedApplication] setStatusBarHidden:NO];
    weakSelf = self;
    [self checkNetWork];
    self.isLoading = true;
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(startLoadingVC:) name:@"startLoadingVC" object:nil];
}

- (void)checkNetWork{
    AFNetworkReachabilityManager *mg = [AFNetworkReachabilityManager sharedManager];
    [mg startMonitoring];
    [mg setReachabilityStatusChangeBlock:^(AFNetworkReachabilityStatus status) {
        if (status == AFNetworkReachabilityStatusNotReachable) {
            [self performSelector:@selector(checkNetWork) withObject:nil afterDelay:1];
        }else{
            [self setupView];
        }
    }];
}
- (void)setupView{
    self.webView.delegate = self;
    self.webView.scrollView.bounces = NO;
    NSURL *url = [NSURL URLWithString:kApiHomeURL];
    NSString *path = [[NSBundle mainBundle] pathForResource:@"dist/index" ofType:@"html"];
//    NSURL *url = [NSURL fileURLWithPath:path];
    [_webView loadRequest:[NSURLRequest requestWithURL:url]];
    self.context = [self.webView valueForKeyPath:@"documentView.webView.mainFrame.javaScriptContext"];
    self.context[@"JSInterface"] = self;
}

- (void)webViewDidStartLoad:(UIWebView *)webView{
}

- (void)webViewDidFinishLoad:(UIWebView *)webView{
    self.isLoading = false;
    [self dismissMessage];
}

- (void)webView:(UIWebView *)webView didFailLoadWithError:(NSError *)error{
    self.isLoading = false;
    [self dismissMessage];
}

- (BOOL)webView:(UIWebView *)webView shouldStartLoadWithRequest:(NSURLRequest *)request navigationType:(UIWebViewNavigationType)navigationType{
    return YES;
}

#define JS调用OPOC
- (NSString *)paramName:(NSDictionary *)paramDict
{
    NSLog(@"paramDict:%@",paramDict);
    NSString *publicTime = [NSString stringWithFormat:@"%ld",(long)([[NSDate new] timeIntervalSince1970] + _timeLong)];
    NSArray *paramNames = [paramDict allKeys];
    NSArray *paramValus = [paramDict allValues];
    NSMutableDictionary *resultParamsDic = [NSMutableDictionary dictionary];
    NSMutableDictionary *paramsDic = [NSMutableDictionary dictionary];
    for (int i = 0; i < paramValus.count; i++) {
        NSString *str1 = paramNames[i];
        NSString *str2 = paramValus[i];
        [paramsDic setObject:str2 forKey:str1];;
        [resultParamsDic setObject:str2 forKey:str1];
    }
    [paramsDic setObject:publicTime forKey:@"time"];
    
    NSString *sign = @"";
    NSArray *sortNames = [paramsDic.allKeys sortedArrayUsingSelector:@selector(compare:)];
    for (NSString *key in sortNames) {
        sign = [sign stringByAppendingString:[key stringByAppendingString:paramsDic[key]]];
    }
    sign = [sign stringByAppendingString:KSecretKey];
    sign = [WTUtil md5:sign];
    [resultParamsDic setObject:[sign lowercaseString] forKey:@"sign"];
    [resultParamsDic setObject:publicTime forKey:@"time"];
    NSData *data = [NSJSONSerialization dataWithJSONObject:resultParamsDic options:NSJSONWritingPrettyPrinted error:nil];
    NSString *text = [[NSString alloc] initWithData:data encoding:NSUTF8StringEncoding];
    NSLog(@"%@", text);
    return text;
}

/**
 打开地图
 
 @param dict 经度纬度
 */
- (void)openMap:(NSDictionary *)dict{
    float lat = [dict[@"lat"] floatValue];
    float lng = [dict[@"lng"] floatValue];
    [YXOpenMap openMapsLat:lat Ing:lng Vc:weakSelf];
}


/**
 获得经纬度
 */
- (void)getLocation{
    [[YXLocationManager sharedManager] getLocationCallBack:^(NSString *lat, NSString *lng, NSString *name) {
        [self setLocationLat:lat Ing:lng];
    }];
}

/**
 是否第一次启动
 */
- (BOOL)getFirstLaunch{
    return ![[NSUserDefaults standardUserDefaults] boolForKey:@"noFirst"];
}

/**
 存储js信息
 */
- (void)saveJSInfo:(NSDictionary *)dict{
    for (NSString *key in dict.allKeys) {
        NSString *value = dict[key];
        [[NSUserDefaults standardUserDefaults] setValue:value forKey:key];
        [[NSUserDefaults standardUserDefaults] synchronize];
    }
}

/**
 增加友盟标签
 */
- (void)addUMTagAlias:(NSDictionary *)dict{
    [AppDelegate addUMTagAlias:dict];
}

/**
 移除友盟标签
 */
- (void)removeAllTagAlias:(NSDictionary *)dict{
    [AppDelegate removeAllTagAlias:dict];
}

/**
 打开浏览器
 */
- (void)openSafariUrl:(NSString *)url{
    if (url && url.length>0) {
        [[UIApplication sharedApplication] openURL:[NSURL URLWithString:url]];
    }
}


/**
 获取js存储信息
 */
- (NSDictionary *)getInfoForKey:(NSString *)key{
    return [[NSUserDefaults standardUserDefaults] valueForKey:key];
}

- (void)createAlertTitle:(NSString *)title Msg:(NSString *)msg{
    UIAlertAction *action = [UIAlertAction actionWithTitle:@"OK" style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action) {
    }];
    UIAlertController *alertVC = [UIAlertController alertControllerWithTitle:title message:msg preferredStyle:UIAlertControllerStyleAlert];
    [alertVC addAction:action];
    [self presentViewController:alertVC animated:YES completion:nil];
}

#pragma mark OC调用js

- (void)setLocationLat:(NSString *)lat Ing:(NSString *)ing{
    NSString *jsStr = [NSString stringWithFormat:@"setLocationLat('%@','%@')",lat,ing];
    [self.context evaluateScript:jsStr];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}

- (void)getTimeLong{
    YSGGetSystemTimeRequest *request = [[YSGGetSystemTimeRequest alloc] init];
    [WTNetWorkUtil requestWTApi:request success:^(WTNetWorkBaseResponse *responseObject) {
        YSGGetSystemTimeResponse *response = (YSGGetSystemTimeResponse *)responseObject;
        NSDictionary *dic = response.data;
        if ([dic objectForKey:@"code"] == 0) {
            _publicTime = [dic objectForKey:@"time"];
            _timeLong = _publicTime.longLongValue - [[NSDate new] timeIntervalSince1970];
        }
        [self requestLunchImage];
    } failure:^(NSError *error) {
        
    } ];
}

- (void)requestLunchImage{
    YSGGetLaunchImageRequest *request = [[YSGGetLaunchImageRequest alloc] init];
    NSString *requestTmp = [NSString stringWithString: [self paramName:nil]];
    NSData *resData = [[NSData alloc] initWithData:[requestTmp dataUsingEncoding:NSUTF8StringEncoding]];
    NSDictionary *resultDic = [NSJSONSerialization JSONObjectWithData:resData options:NSJSONReadingMutableLeaves error:nil];
    [request setValue:resultDic[@"sign"] forKey:@"sign"];
    [request setValue:resultDic[@"time"] forKey:@"time"];
    [WTNetWorkUtil requestWTApi:request success:^(WTNetWorkBaseResponse *responseObject) {
        YSGGetSystemTimeResponse *rsp = (YSGGetSystemTimeResponse *)responseObject;
        [self saveLunchImage];
    } failure:^(NSError *error) {
        
    }];
}

- (void)saveLunchImage{
    NSData *data = [NSData dataWithContentsOfURL:[NSURL URLWithString:@"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498288401603&di=ea58b265b1369fea6945515ecb1876b7&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2F203fb80e7bec54e7ce6c5131b3389b504fc26a19.jpg"]];
    NSFileManager *fileManager = [NSFileManager defaultManager];
    if (![fileManager fileExistsAtPath:GetImgDataDirectory]) {
        [fileManager createDirectoryAtPath:GetImgDataDirectory withIntermediateDirectories:NO attributes:nil error:nil];
    }
    BOOL result = [data writeToFile:GetFileInImgData(@"launch.jpg") atomically:YES];
    if (result) {
        NSLog(@"%@",GetFileInImgData(@"launch.jpg"));
    }
}

- (void)dealloc
{
    [[NSUserDefaults standardUserDefaults] setBool:YES forKey:@"noFirst"];
    [[NSUserDefaults standardUserDefaults] synchronize];
}

- (void)startLoadingVC:(NSNotification *)noti{
    if (self.isLoading) {
        [self pushMessageWaiting];
    }
}


@end
