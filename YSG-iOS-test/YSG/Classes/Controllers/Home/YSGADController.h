//
//  YSGADController.h
//  YSG
//
//  Created by Apple on 2017/6/29.
//  Copyright © 2017年 Apple. All rights reserved.
//

#import "XZViewController.h"

@interface YSGADController : XZViewController
@property (weak, nonatomic) IBOutlet UIWebView *webView;

@property (nonatomic ,strong) NSString *url;
@property (nonatomic ,strong) NSString *content;
@property (nonatomic ,strong) NSString *vcTitle;

@end
