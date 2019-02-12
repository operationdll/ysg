//
//  YXLocationManager.h
//  YSG
//
//  Created by Apple on 2017/6/24.
//  Copyright © 2017年 Apple. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface YXLocationManager : NSObject
+ (YXLocationManager *)sharedManager;

- (void)getLocationCallBack:(void(^)(NSString *lat,NSString *lng,NSString *name))locationback;
@property (copy, nonatomic)  NSDictionary *(^LocationDict)(NSString *lat,NSString *lng);
@property (copy, nonatomic)  void(^LocationName)(NSString *state,NSString *City, NSString *SubLoca);
@end
