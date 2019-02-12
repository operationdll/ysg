//
//  WTNetWorkFileRequest.m
//  OAK-HD
//
//  Created by weitong on 15/9/19.
//  Copyright (c) 2015年 wangtiansoft. All rights reserved.
//

#import "WTNetWorkFileRequest.h"

@implementation WTNetWorkFileRequest

- (instancetype)init
{
    self = [super init];
    if (self) {
        self.moduleName = @"files";
        self.methodName = @"/upload";
        
        NSString *formKey = [NSString stringWithFormat:@"%f",[NSDate timeIntervalSinceReferenceDate]];
        [self setValue:formKey forKey:@"formKey"];
    }
    return self;
}

- (id)initWithFilePath:(NSString *)filePath
{
    self = [self init];
    if (self) {
        if (filePath) {
            self.filePathArray = @[filePath];
        }
    }
    return self;
}

- (id)initWithFilePaths:(NSArray *)filePaths
{
    self = [self init];
    if (self) {
        self.filePathArray = filePaths;
    }
    return self;
}

@end
