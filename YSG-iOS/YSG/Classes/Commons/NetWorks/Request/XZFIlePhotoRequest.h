//
//  XZFIlePhotoRequest.h
//  xinhong
//
//  Created by Apple on 2017/4/10.
//  Copyright © 2017年 Apple. All rights reserved.
//

#import "WTNetWorkBaseRequest.h"

@interface XZFIlePhotoRequest : WTNetWorkBaseRequest

- (instancetype)initWithAuthToken:(NSString *)token Uuid:(NSString *)uuid FileName:(NSString *)fileName;

@end
