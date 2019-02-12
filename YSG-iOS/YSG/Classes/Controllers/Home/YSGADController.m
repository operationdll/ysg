//
//  YSGADController.m
//  YSG
//
//  Created by Apple on 2017/6/29.
//  Copyright © 2017年 Apple. All rights reserved.
//

#import "YSGADController.h"

@interface YSGADController ()<UIWebViewDelegate>
@property (weak, nonatomic) IBOutlet UILabel *titleLabel;

@end

@implementation YSGADController

- (void)viewDidLoad {
    [super viewDidLoad];
    [self setNavLeftBtn];
    if (self.url) {
        [self.webView loadRequest:[NSURLRequest requestWithURL:[NSURL URLWithString:self.url]]];
    }else{
        [self.webView loadHTMLString:self.content baseURL:nil];
    }
    if (self.vcTitle) {
        self.titleLabel.text = self.vcTitle;
    }
}

//导航条设置
- (void)setNavLeftBtn {
    UIButton *btn = [UIButton buttonWithType:UIButtonTypeCustom];
    btn.frame = CGRectMake(10, 0, 32, 32);
    [btn setImage:[UIImage imageNamed:@"公共-导航返回"] forState:UIControlStateNormal];
    [btn addTarget:self action:@selector(navLeftClick:) forControlEvents:UIControlEventTouchUpInside];
    UIBarButtonItem *leftButton = [[UIBarButtonItem alloc] initWithCustomView:btn];
    self.navigationItem.leftBarButtonItem = leftButton;
}
- (IBAction)dismiss:(id)sender {
    [self navLeftClick:nil];
}

- (void)navLeftClick:(UIButton *)btn{
    [self.navigationController popViewControllerAnimated:YES];
}

- (BOOL)webView:(UIWebView *)webView shouldStartLoadWithRequest:(NSURLRequest *)request navigationType:(UIWebViewNavigationType)navigationType{
    return YES;
}
- (void)webViewDidStartLoad:(UIWebView *)webView{
    
}
- (void)webViewDidFinishLoad:(UIWebView *)webView{
    
}
- (void)webView:(UIWebView *)webView didFailLoadWithError:(NSError *)error{
    
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    
}


@end
