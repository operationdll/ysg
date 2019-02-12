//
//  WTNetWorkFileCheckRequest.h
//  OAK-HD
//
//  Created by weitong on 15/9/19.
//  Copyright (c) 2015年 wangtiansoft. All rights reserved.
//

#import "WTNetWorkBaseRequest.h"

@interface WTNetWorkFileCheckRequest : WTNetWorkBaseRequest

- (id)initWithMd5s:(NSArray *)md5s FileNames:(NSArray *)fileNames MimeTypes:(NSArray *)mimeTypes;

@end
