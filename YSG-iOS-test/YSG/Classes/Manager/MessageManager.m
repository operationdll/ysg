//
//  MessageManager.m
//  xinhong
//
//  Created by Apple on 2017/4/6.
//  Copyright © 2017年 Apple. All rights reserved.
//

#import "MessageManager.h"
#import <MBProgressHUD.h>
#import "AppDelegate.h"

@interface MessageManager()

@property(nonatomic,strong) MBProgressHUD       *progressHUD;

@end

@implementation MessageManager

+ (MessageManager *)sharedManager
{
    static MessageManager *sharedManagerInstance = nil;
    static dispatch_once_t predicate; dispatch_once(&predicate, ^{
        sharedManagerInstance = [[self alloc] init];
    });
    return sharedManagerInstance;
}

- (instancetype)init
{
    self = [super init];
    if (self) {
        AppDelegate *app = (AppDelegate *)[UIApplication sharedApplication].delegate;
        UIWindow *window = [[[UIApplication sharedApplication] delegate] window];
        self.progressHUD = [[MBProgressHUD alloc] initWithView:app.window.rootViewController.view];
        [window addSubview:self.progressHUD];
        [self.progressHUD bringSubviewToFront:window];
    }
    return self;
}

- (void)pushMessageWaiting
{
    [self pushMessage:@"请稍候..."];
}

- (void)pushMessageFailure
{
    [self pushMessage:@"请求失败" withDelay:1];
}

- (void)pushMessage:(NSString *)msg
{
    [self.progressHUD setMode:MBProgressHUDModeIndeterminate];
    [self.progressHUD setLabelText:msg];
    [self.progressHUD show:YES];
}

- (void)pushMessage:(NSString *)msg withDelay:(NSUInteger)delay
{
    [self pushMessage:msg];
    [self.progressHUD setMode:MBProgressHUDModeText];
    [self.progressHUD hide:YES afterDelay:delay];
}

- (void)dismissMessage
{
    [self.progressHUD hide:YES];
}

- (void)dismissMessage:(NSString *)msg
{
    [self.progressHUD setMode:MBProgressHUDModeText];
    [self.progressHUD setLabelText:msg];
    [self.progressHUD hide:YES afterDelay:1];
}


@end
