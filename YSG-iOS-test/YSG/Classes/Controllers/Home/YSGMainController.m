//
//  YSGMainController.m
//  YSG
//
//  Created by Apple on 2017/6/24.
//  Copyright © 2017年 Apple. All rights reserved.
//

#import "YSGMainController.h"
#import "XZConstans.h"
#import <WebKit/WebKit.h>
#import <AFNetworkReachabilityManager.h>
#import "WTNetWorkUtil.h"
#import "XZFIlePhotoRequest.h"
#import "XZFIlePhotoResponse.h"
#import "WTUtil.h"
#import "YSGGetSystemTimeRequest.h"
#import "YSGGetSystemTimeResponse.h"
#import "YSGGetLaunchImageRequest.h"
#import "YSGGetLaunchImageResponse.h"
#import "YXOpenMap.h"
#import "YXLocationManager.h"
#import "JSInterface.h"
#import "JSInterface+YSG.h"
#import "AppDelegate.h"
#import "YSGPDFVideoController.h"
@interface YSGMainController()<WKUIDelegate,WKNavigationDelegate,WKScriptMessageHandler,UIImagePickerControllerDelegate,UINavigationControllerDelegate>
@property (strong, nonatomic) WKWebView *webView;
@property (nonatomic,strong) NSString  *publicTime;
@property (nonatomic,strong) NSMutableDictionary  *params;
@property (nonatomic,strong) NSDictionary *result;
@property (nonatomic,assign) long timeLong;
@property (nonatomic,assign) BOOL  isLoading;
@end

@implementation YSGMainController

- (void)viewDidLoad {
    [super viewDidLoad];
    [self checkNetWork];
    self.automaticallyAdjustsScrollViewInsets = NO;
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

- (void)viewWillAppear:(BOOL)animated{
    [super viewWillAppear:animated];
    NSLog(@"%@",self.view);
}

- (void)setupView{
    [YXLocationManager sharedManager];
    WKUserContentController *userContentController = [[WKUserContentController alloc] init];
    [userContentController addScriptMessageHandler:self name:@"getFirstLaunch"];
    [userContentController addScriptMessageHandler:self name:@"openMap"];
    [userContentController addScriptMessageHandler:self name:@"requestParamName"];
    [userContentController addScriptMessageHandler:self name:@"saveJSInfo"];
    [userContentController addScriptMessageHandler:self name:@"getLocation"];
    [userContentController addScriptMessageHandler:self name:@"addUMTagAlias"];
    [userContentController addScriptMessageHandler:self name:@"removeAllTagAlias"];
    [userContentController addScriptMessageHandler:self name:@"openFile"];
    [userContentController addScriptMessageHandler:self name:@"openSafariUrl"];
    
    WKWebViewConfiguration *configuration = [[WKWebViewConfiguration alloc] init];
    configuration.userContentController = userContentController;
    WKUserScript *usrScript = [[WKUserScript alloc] initWithSource:[JSInterface shareInstance].handlerJS injectionTime:WKUserScriptInjectionTimeAtDocumentEnd forMainFrameOnly:YES];
    
    // 通过JS与webview内容交互
    configuration.userContentController = userContentController;
    
    [configuration.userContentController addUserScript:usrScript];
    // 注入JS对象名称AppModel，当JS通过AppModel来调用时，
    // 我们可以在WKScriptMessageHandler代理中接收到
    [configuration.userContentController addScriptMessageHandler:[JSInterface shareInstance] name:EventHandler];
    configuration.preferences.javaScriptCanOpenWindowsAutomatically = YES;
    _webView = [[WKWebView alloc] initWithFrame:CGRectMake(0, 0, kScreenWidth, kScreenHeight) configuration:configuration];
    [self.view addSubview:_webView];
    NSURL *baseURL = [NSURL URLWithString:kApiHomeURL];
    self.webView.UIDelegate = self;
    
    if (@available(iOS 11, *)) {
        self.webView.scrollView.contentInsetAdjustmentBehavior =     UIScrollViewContentInsetAdjustmentNever;
    }
    self.webView.scrollView.bounces = NO;
    self.webView.navigationDelegate = self;
    self.webView.allowsBackForwardNavigationGestures = NO;
    self.webView.allowsLinkPreview = NO;
    NSString *path = [[NSBundle mainBundle] pathForResource:@"dist/index" ofType:@"html"];
    //    baseURL = [NSURL fileURLWithPath:path];
    [_webView loadRequest:[NSURLRequest requestWithURL:baseURL]];
    [JSInterface getInject:self.webView];
}

#pragma WKUIDelegate
- (void)webView:(WKWebView *)webView runJavaScriptTextInputPanelWithPrompt:(NSString *)prompt defaultText:(nullable NSString *)defaultText initiatedByFrame:(WKFrameInfo *)frame completionHandler:(void (^)(NSString * __nullable result))completionHandler{
    UIAlertController *alert = [UIAlertController alertControllerWithTitle:webView.title message:@"JS调用输入框" preferredStyle:UIAlertControllerStyleAlert];
    [alert addTextFieldWithConfigurationHandler:^(UITextField * _Nonnull textField) {
        textField.textColor = [UIColor redColor];
    }];
    
    [alert addAction:[UIAlertAction actionWithTitle:@"确定" style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action) {
        completionHandler([[alert.textFields lastObject] text]);
    }]];
    [self presentViewController:alert animated:YES completion:NULL];
}

- (void)webView:(WKWebView *)webView runJavaScriptConfirmPanelWithMessage:(NSString *)message initiatedByFrame:(WKFrameInfo *)frame completionHandler:(void (^)(BOOL result))completionHandler{
    UIAlertController *alert = [UIAlertController alertControllerWithTitle:webView.title message:message preferredStyle:UIAlertControllerStyleAlert];
    [alert addAction:[UIAlertAction actionWithTitle:@"确定" style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action) {
        completionHandler(YES);
    }]];
    [alert addAction:[UIAlertAction actionWithTitle:@"取消" style:UIAlertActionStyleCancel handler:^(UIAlertAction * _Nonnull action) {
        completionHandler(NO);
    }]];
    [self presentViewController:alert animated:YES completion:NULL];
}

- (void)webView:(WKWebView *)webView runJavaScriptAlertPanelWithMessage:(NSString *)message initiatedByFrame:(WKFrameInfo *)frame completionHandler:(void (^)(void))completionHandler{
    UIAlertController *alert = [UIAlertController alertControllerWithTitle:webView.title message:message preferredStyle:UIAlertControllerStyleAlert];
    [alert addAction:[UIAlertAction actionWithTitle:@"确定" style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action) {
        completionHandler();
    }]];
    
    [self presentViewController:alert animated:YES completion:NULL];
}
#pragma WKNavigationDelegate
- (void)webView:(WKWebView *)webView didReceiveServerRedirectForProvisionalNavigation:(WKNavigation *)navigation{
}

- (void)webView:(WKWebView *)webView decidePolicyForNavigationResponse:(WKNavigationResponse *)navigationResponse decisionHandler:(void (^)(WKNavigationResponsePolicy))decisionHandler{
    decisionHandler(WKNavigationResponsePolicyAllow);
}


- (void)webView:(WKWebView *)webView decidePolicyForNavigationAction:(WKNavigationAction *)navigationAction decisionHandler:(void (^)(WKNavigationActionPolicy))decisionHandler{
    decisionHandler(WKNavigationActionPolicyAllow);
}

- (void)webView:(WKWebView *)webView didStartProvisionalNavigation:(WKNavigation *)navigation{
    
}

- (void)webView:(WKWebView *)webView didCommitNavigation:(WKNavigation *)navigation{
    
}

- (void)webView:(WKWebView *)webView didFinishNavigation:(WKNavigation *)navigation{
    self.isLoading = false;
    [self dismissMessage];
}

- (void)webView:(WKWebView *)webView didFailProvisionalNavigation:(WKNavigation *)navigation{
    self.isLoading = false;
    [self dismissMessage];
}

#pragma WKScriptMessageHandler
- (void)getInfoFromNative:(id)params :(void(^)(id response))callBack{
    NSLog(@"params %@",params);
    NSString *str = @"'Hi Jack!'";
    callBack(str);
    
}
- (void)userContentController:(WKUserContentController *)userContentController didReceiveScriptMessage:(WKScriptMessage *)message{
    
    NSString *methods = [NSString stringWithFormat:@"%@:", message.name];
    SEL selector = NSSelectorFromString(methods);
    // 调用方法
    if ([self respondsToSelector:selector]) {
        NSLog(@"%@",message.body);
        [self performSelector:selector withObject:message.body];
    }
}

#define JS调用OPOC
- (NSString *)requestParamName:(NSDictionary *)paramDict
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
    [YXOpenMap openMapsLat:lat Ing:lng Vc:self];
}


/**
 获得经纬度
 */
- (void)getLocation:(NSString *)body{
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
 打开pdf或者视频
 */
- (void)openFile:(NSString *)url{
    if (url==nil || url.length==0) {
        [self pushMessage:@"文件地址错误" withDelay:1.5];
        return;
    }
    YSGPDFVideoController *vc = [[YSGPDFVideoController alloc] init];
    vc.url = url;
    [self presentViewController:vc animated:YES completion:nil];
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
    [self.webView evaluateJavaScript:jsStr completionHandler:^(id _Nullable data, NSError * _Nullable error) {
        if (error) {
            NSLog(@"错误:%@", error.localizedDescription);
        }
    }];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
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

- (void)startLoadingVC:(NSNotification *)noti{
    if (self.isLoading) {
        [self pushMessageWaiting];
    }
}



@end
