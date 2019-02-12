//
//  YSGPDFVideoController.m
//  YSG
//
//  Created by Apple on 2017/7/14.
//  Copyright © 2017年 Apple. All rights reserved.
//

#import "YSGPDFVideoController.h"
#import <WebKit/WebKit.h>
#import "XZConstans.h"
@interface YSGPDFVideoController ()

@property (nonatomic,strong) UIButton *btn;
@property (nonatomic,assign) BOOL flag;
@property (nonatomic,strong) NSTimer *timer;

@end

@implementation YSGPDFVideoController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    [self setupView];
}
- (void)setupView{
    WKWebViewConfiguration *configuration = [[WKWebViewConfiguration alloc] init];
    WKWebView *webView = [[WKWebView alloc] initWithFrame:CGRectMake(0, 0, kScreenWidth, kScreenHeight) configuration:configuration];
    [self.view addSubview:webView];
    UITapGestureRecognizer *tap = [[UITapGestureRecognizer alloc] initWithTarget:self action:@selector(tapWebViwe)];
    webView.userInteractionEnabled = YES;
    [webView addGestureRecognizer:tap];
    NSURL *baseURL = [NSURL URLWithString:self.url];
    webView.allowsBackForwardNavigationGestures = NO;
    [webView loadRequest:[NSURLRequest requestWithURL:baseURL]];
    _btn = [[UIButton alloc] initWithFrame:CGRectMake(kScreenWidth-60, 30, 50, 50)];
    [_btn setImage:[UIImage imageNamed:@"back.png"] forState:UIControlStateNormal];
    _btn.imageEdgeInsets = UIEdgeInsetsMake(15, 15, 15, 15);
    [self.view addSubview:_btn];
    [_btn addTarget:self action:@selector(dismissView:) forControlEvents:UIControlEventTouchUpInside];
    if ([self.url hasSuffix:@"pdf"] || [self.url hasSuffix:@"PDF"]) {
        
    }
//    self.timer = [NSTimer scheduledTimerWithTimeInterval:3 target:self selector:@selector(hiddenBtn) userInfo:nil repeats:YES];
}

- (void)dismissView:(UIButton *)btn{
    [self dismissViewControllerAnimated:YES completion:nil];
}

- (void)touchesBegan:(NSSet<UITouch *> *)touches withEvent:(UIEvent *)event{
    
}

- (void)tapWebViwe{
    _btn.hidden = NO;
    self.flag = YES;
    [self.view.window bringSubviewToFront:_btn];
    [self.timer setFireDate:[NSDate distantPast]];
}

- (void)hiddenBtn{
    if (!self.flag) {
        _btn.hidden = YES;
        [self.timer setFireDate:[NSDate distantFuture]];
    }
    self.flag = NO;
}

- (void)dealloc
{
    if ([[UIDevice currentDevice].systemVersion floatValue] >=9.0) {
        NSSet *websiteDataTypes = [NSSet setWithArray:@[
                                                        WKWebsiteDataTypeDiskCache,
                                                        WKWebsiteDataTypeOfflineWebApplicationCache,
                                                        WKWebsiteDataTypeMemoryCache,
                                                        WKWebsiteDataTypeCookies,
                                                        WKWebsiteDataTypeSessionStorage,
                                                        WKWebsiteDataTypeIndexedDBDatabases,
                                                        WKWebsiteDataTypeWebSQLDatabases
                                                        ]];
        //NSSet *websiteDataTypes = [WKWebsiteDataStore allWebsiteDataTypes];
        NSDate *dateFrom = [NSDate dateWithTimeIntervalSince1970:0];
        [[WKWebsiteDataStore defaultDataStore] removeDataOfTypes:websiteDataTypes
                                                   modifiedSince:dateFrom completionHandler:^{
                                                       // code
                                                   }];
    }
}

@end
