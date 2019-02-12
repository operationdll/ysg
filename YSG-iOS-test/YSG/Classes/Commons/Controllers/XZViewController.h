//
//  XZViewController.h
//  xinhong
//
//  Created by Apple on 2017/4/6.
//  Copyright © 2017年 Apple. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "MessageManager.h"
@interface XZViewController : UIViewController
//  Message
- (void)pushMessageWaiting;
- (void)pushMessageFailure;
- (void)pushMessage:(NSString *)msg;
- (void)pushMessage:(NSString *)msg withDelay:(NSUInteger)delay;
- (void)dismissMessage;
- (void)dismissMessage:(NSString *)msg;

@end
