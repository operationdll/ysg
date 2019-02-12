//
//  NSString+Contains.m
//  blueTooth-test
//
//  Created by 张艳雪 on 16/7/22.
//  Copyright © 2016年 wangtiansoft. All rights reserved.
//

#import "NSString+Contains.h"

@implementation NSString (Contains)

- (BOOL)wtContainsString:(NSString *)str{
    return [self rangeOfString:str].location != NSNotFound;
}

@end
