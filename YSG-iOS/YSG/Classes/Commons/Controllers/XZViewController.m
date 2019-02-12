//
//  XZViewController.m
//  xinhong
//
//  Created by Apple on 2017/4/6.
//  Copyright © 2017年 Apple. All rights reserved.
//

#import "XZViewController.h"
#import "MessageManager.h"
@interface XZViewController ()

@end

@implementation XZViewController

- (void)viewDidLoad {
    [super viewDidLoad];

}

#pragma mark - 消息提示

- (void)pushMessageWaiting
{
    [[MessageManager sharedManager] pushMessageWaiting];
}

- (void)pushMessageFailure
{
    [[MessageManager sharedManager] pushMessageFailure];
}

- (void)pushMessage:(NSString *)msg
{
    
    [[MessageManager sharedManager] pushMessage:msg];
}

- (void)pushMessage:(NSString *)msg withDelay:(NSUInteger)delay
{
    [[MessageManager sharedManager] pushMessage:msg withDelay:delay];
}

- (void)dismissMessage
{
    [[MessageManager sharedManager] dismissMessage];
}

- (void)dismissMessage:(NSString *)msg
{
    [[MessageManager sharedManager] dismissMessage:msg];
}


@end
