//
//  WTNetWorkFileCheckResponse.h
//  OAK-HD
//
//  Created by weitong on 15/9/19.
//  Copyright (c) 2015年 wangtiansoft. All rights reserved.
//

#import "WTNetWorkBaseResponse.h"
#import "LBYLSignCheck.h"
@interface WTNetWorkFileCheckResponse : WTNetWorkBaseResponse

@property(nonatomic,strong) LBYLSignCheck            *data;

@end
