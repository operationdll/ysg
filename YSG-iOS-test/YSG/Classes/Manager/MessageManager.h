//
//  MessageManager.h
//  xinhong
//
//  Created by Apple on 2017/4/6.
//  Copyright © 2017年 Apple. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface MessageManager : NSObject

+ (MessageManager *)sharedManager;

- (void)pushMessageWaiting;
- (void)pushMessageFailure;
- (void)pushMessage:(NSString *)msg;
- (void)pushMessage:(NSString *)msg withDelay:(NSUInteger)delay;
- (void)dismissMessage;
- (void)dismissMessage:(NSString *)msg;

@end
