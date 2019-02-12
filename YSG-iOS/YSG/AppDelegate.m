//
//  AppDelegate.m
//  YSG
//
//  Created by Apple on 2017/6/20.
//  Copyright © 2017年 Apple. All rights reserved.
//

#import "AppDelegate.h"
#import "UMessage.h"
#if __IPHONE_OS_VERSION_MAX_ALLOWED >= 100000
#import <UserNotifications/UserNotifications.h>
#endif
//以下几个库仅作为调试引用引用的
#import <AdSupport/AdSupport.h>
#import <CommonCrypto/CommonDigest.h>
#import "YSGMainController.h"
#import "XZConstans.h"
#import "YSGWebViewController.h"
#import "XZGuideController.h"
#import "YSGGetLaunchImageRequest.h"
#import "YSGGetLaunchImageResponse.h"
#import "WTNetWorkUtil.h"
#import "YSGGetSystemTimeRequest.h"
#import "YSGGetSystemTimeResponse.h"
#import "WTUtil.h"
#import <UIImageView+WebCache.h>
#import "XZConstans.h"
#import "YSGADController.h"
#import "MessageManager.h"
@interface AppDelegate ()<UNUserNotificationCenterDelegate>
@property (nonatomic,strong) UIView *lunchView;
@property (nonatomic,strong) NSString  *publicTime;
@property (nonatomic,strong) NSMutableDictionary  *params;
@property (nonatomic,strong) NSDictionary *result;
@property (nonatomic,assign) long timeLong;
@property (nonatomic,strong) XZGuideController *guidVC;
@property (nonatomic,strong) NSDictionary *dic;
@property (nonatomic,strong) UIStoryboard *storyB;

@end

@implementation AppDelegate


- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions {
    self.storyB = [UIStoryboard storyboardWithName:@"Main" bundle:nil];
    [self showGuide];
    [self setPushOptions:launchOptions];
    return YES;
}
- (void)showGuide{
    
    self.window = [[UIWindow alloc] initWithFrame:[[UIScreen mainScreen] bounds]];
    self.window.backgroundColor = [UIColor whiteColor];
    [self showMainVC];
    [self.window makeKeyAndVisible];
    [self showlunchView:nil];
    [self getTimeLong];
}

- (void)showMainVC{
    if ([[UIDevice currentDevice].systemVersion floatValue] >=9.0) {
        YSGMainController *controller = [self.storyB instantiateViewControllerWithIdentifier:@"YSGMainController"];
        controller.modalTransitionStyle = UIModalTransitionStyleCrossDissolve;
        UINavigationController *nav = [[UINavigationController alloc] initWithRootViewController:controller];
        nav.navigationBarHidden = YES;
        self.window.rootViewController = nav;
    }else{
        YSGWebViewController *controller = [self.storyB instantiateViewControllerWithIdentifier:@"YSGWebViewController"];
        controller.modalTransitionStyle = UIModalTransitionStyleCrossDissolve;
        UINavigationController *nav = [[UINavigationController alloc] initWithRootViewController:controller];
        nav.navigationBarHidden = YES;
        self.window.rootViewController = nav;
    }
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
        [self removeLaunchView];
    } ];
}
- (void)requestLunchImage{
    YSGGetLaunchImageRequest *request = [[YSGGetLaunchImageRequest alloc] init];
    NSString *requestTmp = [NSString stringWithString: [self paramName:@{@"groupid":@"1"}]];
    NSData *resData = [[NSData alloc] initWithData:[requestTmp dataUsingEncoding:NSUTF8StringEncoding]];
    NSDictionary *resultDic = [NSJSONSerialization JSONObjectWithData:resData options:NSJSONReadingMutableLeaves error:nil];
    [request setValue:resultDic[@"sign"] forKey:@"sign"];
    [request setValue:resultDic[@"time"] forKey:@"time"];
    [request setValue:@"1" forKey:@"groupid"];
    [WTNetWorkUtil requestWTApi:request success:^(WTNetWorkBaseResponse *responseObject) {
        YSGGetSystemTimeResponse *rsp = (YSGGetSystemTimeResponse *)responseObject;
        NSDictionary *dic = rsp.data[@"list"];
        if (dic && dic.allKeys.count>0) {
            self.dic = dic;
            UIImageView *imageV = [self.lunchView viewWithTag:100];
            UIButton *btn = [self.lunchView viewWithTag:200];
            if (dic.allKeys.count >0) {
                [imageV sd_setImageWithURL:dic[@"pic"] placeholderImage:[UIImage imageNamed:@"launch.jpg"]];
                btn.hidden = NO;
                [self performSelector:@selector(removeLaunchView) withObject:nil afterDelay:3];
            }else{
                [self removeLaunchView];
            }
        }else{
            [self removeLaunchView];
        }
    } failure:^(NSError *error) {
        [self removeLaunchView];
    }];
}

- (void)removeLaunchView{
    [[NSNotificationCenter defaultCenter] postNotificationName:@"startLoadingVC" object:nil];
    [self.lunchView removeFromSuperview];
}

- (void)showlunchView:(NSURL *)url{
    CGRect react = CGRectMake(0, 0, self.window.screen.bounds.size.width, self.window.screen.bounds.size.height);
    self.lunchView = [[UIView alloc]initWithFrame:react];
    [self.window addSubview:self.lunchView];
    UIImageView *imageV = [[UIImageView alloc] initWithFrame:react];
    imageV.tag = 100;
    [imageV sd_setImageWithURL:url placeholderImage:[UIImage imageNamed:@"launch.jpg"]];
    imageV.contentMode = UIViewContentModeScaleAspectFill;
    [self.lunchView addSubview:imageV];
    UIButton *btn = [[UIButton alloc] initWithFrame:CGRectMake(kScreenWidth - 60, 40, 40, 40)];
    [btn setTitle:@"跳过" forState:UIControlStateNormal];
    [btn setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    [btn setBackgroundColor:[UIColor colorWithRed:0 green:0 blue:0 alpha:0.5]];
    btn.layer.cornerRadius = 20;
    btn.titleLabel.font = [UIFont systemFontOfSize:10];
    btn.layer.masksToBounds = YES;
    btn.layer.borderWidth = 1;
    btn.layer.borderColor = [UIColor whiteColor].CGColor;
    btn.tag = 200;
    btn.hidden = YES;
    [btn addTarget:self action:@selector(onClickRm:) forControlEvents:UIControlEventTouchDragInside];
    [self.lunchView addSubview:btn];
    imageV.userInteractionEnabled = YES;
    UITapGestureRecognizer *tap = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(toADView:)];
    [imageV addGestureRecognizer:tap];
    [self.window bringSubviewToFront:self.lunchView];
}

- (void)onClickRm:(UIButton *)btn{
    [self removeLaunchView];
}

- (void)toADView:(id)tap{
    if (self.dic) {
        [self.lunchView removeFromSuperview];
        YSGADController *vc = [self.storyB instantiateViewControllerWithIdentifier:@"YSGADController"];
        vc.url = self.dic[@"link"];
        UINavigationController *nav =  (UINavigationController *)self.window.rootViewController;
        [nav pushViewController:vc animated:YES];
    }
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

- (void)setPushOptions:(NSDictionary *)launchOptions{
    [UMessage startWithAppkey:@"5948e79df43e48682f000049" launchOptions:launchOptions httpsEnable:YES];
    //注册通知，如果要使用category的自定义策略，可以参考demo中的代码。
    [UMessage registerForRemoteNotifications];
    
    //iOS10必须加下面这段代码。
    UNUserNotificationCenter *center = [UNUserNotificationCenter currentNotificationCenter];
    center.delegate=self;
    UNAuthorizationOptions types10=UNAuthorizationOptionBadge|  UNAuthorizationOptionAlert|UNAuthorizationOptionSound;
    [center requestAuthorizationWithOptions:types10     completionHandler:^(BOOL granted, NSError * _Nullable error) {
        if (granted) {
            //点击允许
            //这里可以添加一些自己的逻辑
        } else {
            //点击不允许
            //这里可以添加一些自己的逻辑
        }
    }];
    [UMessage setChannel:@"App Store"];
    [UMessage setLogEnabled:YES];
}

+ (void)addUMTagAlias:(NSDictionary *)dic{
    if (![dic[@"tag"] isKindOfClass:[NSArray class]]) {
        [[MessageManager sharedManager] pushMessage:@"tag格式不正确" withDelay:1.5];
        return;
    }
    [self addUMTag:dic[@"tag"] AliasType:dic[@"alias_type"] Alias:dic[@"alias"]];
}

+ (void)addUMTag:(NSArray *)tags AliasType:(NSString *)type Alias:(NSString *)alias{
    for (NSString *tag in tags) {
        [UMessage addTag:tag
                response:^(id responseObject, NSInteger remain, NSError *error) {
                    //add your codes
                }];
    }
    [UMessage removeAlias:alias type:type response:^(id responseObject, NSError *error) {
        [UMessage addAlias:alias type:type response:^(id responseObject, NSError *error) {
            
        }];
    }];
}

+ (void)removeAllTagAlias:(NSDictionary *)dic{
    
    [UMessage removeAlias:dic[@"alias"] type:dic[@"alias_type"] response:^(id  _Nonnull responseObject, NSError * _Nonnull error) {
        
    }];
    
    if (![dic[@"tag"] isKindOfClass:[NSArray class]]) {
        [[MessageManager sharedManager] pushMessage:@"tag格式不正确" withDelay:1.5];
        return;
    }
    for (NSString *tag in dic[@"tag"]) {
        [UMessage removeTag:tag response:^(id  _Nonnull responseObject, NSInteger remain, NSError * _Nonnull error) {
            
        }];
    }
}
// iOS <=9 点击通知栏
- (void)application:(UIApplication *)application didReceiveRemoteNotification:(NSDictionary *)userInfo
{
    //应用处于后台时的远程推送接受
    //必须加这句代码
    [UMessage didReceiveRemoteNotification:userInfo];
    YSGADController *vc = [self.storyB instantiateViewControllerWithIdentifier:@"YSGADController"];
    vc.vcTitle = userInfo[@"aps"][@"alert"];
    vc.url = [NSString stringWithFormat:@"%@",userInfo[@"value"]];
    UINavigationController *nav =  (UINavigationController *)self.window.rootViewController;
    [nav pushViewController:vc animated:YES];
}


#pragma mark - 收到远程推送
// iOS <=9 在前台收到通知
- (void)application:(UIApplication *)application didReceiveRemoteNotification:(NSDictionary *)userInfo fetchCompletionHandler:(void (^)(UIBackgroundFetchResult))completionHandler {
    completionHandler(UIBackgroundFetchResultNewData);
    //应用处于前台时的远程推送接受
    //关闭U-Push自带的弹出框
    [UMessage setAutoAlert:NO];
    UIAlertAction *done = [UIAlertAction actionWithTitle:@"确定" style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action) {
        YSGADController *vc = [self.storyB instantiateViewControllerWithIdentifier:@"YSGADController"];
        vc.url = [NSString stringWithFormat:@"%@",userInfo[@"value"]];
        vc.vcTitle = userInfo[@"aps"][@"alert"];
        UINavigationController *nav =  (UINavigationController *)self.window.rootViewController;
        [nav pushViewController:vc animated:YES];
    }];
    UIAlertAction *cancel = [UIAlertAction actionWithTitle:@"忽略" style:UIAlertActionStyleCancel handler:nil];
    UIAlertController *alertVC = [UIAlertController alertControllerWithTitle:userInfo[@"aps"][@"alert"] message:@"" preferredStyle:UIAlertControllerStyleAlert];
    [alertVC addAction:done];
    [alertVC addAction:cancel];
    [self.window.rootViewController presentViewController:alertVC animated:YES completion:nil];
    //必须加这句代码
    [UMessage didReceiveRemoteNotification:userInfo];
}

//iOS10新增：处理前台收到通知的代理方法
-(void)userNotificationCenter:(UNUserNotificationCenter *)center willPresentNotification:(UNNotification *)notification withCompletionHandler:(void (^)(UNNotificationPresentationOptions))completionHandler{
    NSDictionary * userInfo = notification.request.content.userInfo;
    if([notification.request.trigger isKindOfClass:[UNPushNotificationTrigger class]]) {
        //应用处于前台时的远程推送接受
        //关闭U-Push自带的弹出框
        [UMessage setAutoAlert:NO];
        UIAlertAction *done = [UIAlertAction actionWithTitle:@"确定" style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action) {
            YSGADController *vc = [self.storyB instantiateViewControllerWithIdentifier:@"YSGADController"];
            vc.url = [NSString stringWithFormat:@"%@",userInfo[@"value"]];
            vc.vcTitle = userInfo[@"aps"][@"alert"];
            UINavigationController *nav =  (UINavigationController *)self.window.rootViewController;
            [nav pushViewController:vc animated:YES];
        }];
        UIAlertAction *cancel = [UIAlertAction actionWithTitle:@"忽略" style:UIAlertActionStyleCancel handler:nil];
        UIAlertController *alertVC = [UIAlertController alertControllerWithTitle:userInfo[@"aps"][@"alert"] message:@"" preferredStyle:UIAlertControllerStyleAlert];
        [alertVC addAction:done];
        [alertVC addAction:cancel];
        [self.window.rootViewController presentViewController:alertVC animated:YES completion:nil];
        //必须加这句代码
        [UMessage didReceiveRemoteNotification:userInfo];

    }else{
        //应用处于前台时的本地推送接受
    }
    //当应用处于前台时提示设置，需要哪个可以设置哪一个
    completionHandler(UNNotificationPresentationOptionSound|UNNotificationPresentationOptionBadge|UNNotificationPresentationOptionAlert);
}

//iOS10新增：处理后台点击通知的代理方法
-(void)userNotificationCenter:(UNUserNotificationCenter *)center didReceiveNotificationResponse:(UNNotificationResponse *)response withCompletionHandler:(void (^)())completionHandler{
    NSDictionary * userInfo = response.notification.request.content.userInfo;
    if([response.notification.request.trigger isKindOfClass:[UNPushNotificationTrigger class]]) {
        //应用处于后台时的远程推送接受
        //必须加这句代码
        [UMessage didReceiveRemoteNotification:userInfo];
        YSGADController *vc = [self.storyB instantiateViewControllerWithIdentifier:@"YSGADController"];
        vc.vcTitle = userInfo[@"aps"][@"alert"];
        vc.url = [NSString stringWithFormat:@"%@",userInfo[@"value"]];
        UINavigationController *nav =  (UINavigationController *)self.window.rootViewController;
        [nav pushViewController:vc animated:YES];
    }else{
        //应用处于后台时的本地推送接受
    }
}


- (void)applicationWillResignActive:(UIApplication *)application {
    
}


- (void)applicationDidEnterBackground:(UIApplication *)application {
    
}


- (void)applicationWillEnterForeground:(UIApplication *)application {
    
}


- (void)applicationDidBecomeActive:(UIApplication *)application {
    
}


- (void)applicationWillTerminate:(UIApplication *)application {
    
}





@end
