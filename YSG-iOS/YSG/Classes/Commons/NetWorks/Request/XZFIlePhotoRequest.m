//
//  XZFIlePhotoRequest.m
//  xinhong
//
//  Created by Apple on 2017/4/10.
//  Copyright © 2017年 Apple. All rights reserved.
//

#import "XZFIlePhotoRequest.h"

@implementation XZFIlePhotoRequest

- (instancetype)init
{
    self = [super init];
    if (self) {
        self.moduleName = @"api/user";
        self.methodName = @"photo";
        self.header = [NSMutableDictionary dictionary];
    }
    return self;
}

- (instancetype)initWithAuthToken:(NSString *)token Uuid:(NSString *)uuid FileName:(NSString *)fileName{
    self = [self init];
    if (self) {
        [self setValue:fileName forKey:@"photo"];
        [self setValue:uuid forKey:@"userUuid"];
        [self.header setObject:@"authToken" forKey:@"key"];
        [self.header setObject:token forKey:@"value"];
    }
    return self;
}

@end
