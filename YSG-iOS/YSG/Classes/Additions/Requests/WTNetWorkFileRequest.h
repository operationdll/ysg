//
//  WTNetWorkFileRequest.h
//  OAK-HD
//
//  Created by weitong on 15/9/19.
//  Copyright (c) 2015年 wangtiansoft. All rights reserved.
//

#import "WTNetWorkBaseRequest.h"

@interface WTNetWorkFileRequest : WTNetWorkBaseRequest

@property(nonatomic,strong) NSArray             *filePathArray;

- (id)initWithFilePath:(NSString *)filePath;
- (id)initWithFilePaths:(NSArray *)filePaths;

@end
