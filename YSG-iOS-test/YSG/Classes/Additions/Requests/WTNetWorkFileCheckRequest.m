//
//  WTNetWorkFileCheckRequest.m
//  OAK-HD
//
//  Created by weitong on 15/9/19.
//  Copyright (c) 2015年 wangtiansoft. All rights reserved.
//

#import "WTNetWorkFileCheckRequest.h"
#import "WTUtil.h"

@implementation WTNetWorkFileCheckRequest

- (instancetype)init
{
    self = [super init];
    if (self) {
        self.moduleName = @"files";
        self.methodName = @"signcheck";
    }
    return self;
}

- (id)initWithMd5s:(NSArray *)md5s FileNames:(NSArray *)fileNames MimeTypes:(NSArray *)mimeTypes;
{
    self = [self init];
    if (self) {
        if (md5s && fileNames && mimeTypes) {
            [self setValue:md5s forKey:@"md5s"];
            [self setValue:fileNames forKey:@"file_names"];
            [self setValue:mimeTypes forKey:@"mime_types"];
        }
    }
    return self;
}

@end
