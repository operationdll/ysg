//
//  XZGuideController.m
//  xinhong
//
//  Created by Apple on 2017/4/6.
//  Copyright © 2017年 Apple. All rights reserved.
//

#import "XZGuideController.h"
#import "XZConstans.h"
#import "YSGWebViewController.h"
#import <UIImageView+WebCache.h>
@interface XZGuideController ()<UIScrollViewDelegate>

@property (weak, nonatomic) IBOutlet UIScrollView *mainScrollView;
@end

@implementation XZGuideController

- (void)viewDidLoad {
    [super viewDidLoad];
    _mainScrollView.delegate = self;
    _mainScrollView.contentSize = CGSizeMake([UIScreen mainScreen].bounds.size.width*self.images.count, 0);
    _mainScrollView.pagingEnabled = YES;    
    for (int i=1 ;i<=self.images.count ;i++) {
        NSString *url = self.images[i];
        UIImageView *imgview = [[UIImageView alloc] init];
        imgview.frame = CGRectMake([UIScreen mainScreen].bounds.size.width*(i-1), 0, [UIScreen mainScreen].bounds.size.width, [UIScreen mainScreen].bounds.size.height);
        [imgview sd_setImageWithURL:[NSURL URLWithString:url]];
        imgview.contentMode = UIViewContentModeScaleAspectFill;
        if (i == self.images.count - 1) {
            imgview.userInteractionEnabled = YES;
            UIButton *btn = [[UIButton alloc] initWithFrame:CGRectMake((kScreenWidth - kScreenWidth*105/375)/2, kScreenHeight - 90, kScreenWidth*105/375, 45)];
            btn.layer.cornerRadius = 7;
            [btn addTarget:self action:@selector(onClickSelf) forControlEvents:UIControlEventTouchUpInside];
            [imgview addSubview:btn];
        }
        [_mainScrollView addSubview:imgview];
        
        UIButton *btn = [UIButton buttonWithType:UIButtonTypeCustom];
        btn.frame = CGRectMake(20 + kScreenWidth * (i-1), 20, 100, 50);
        [btn setBackgroundColor:[UIColor clearColor]];
        [btn addTarget:self action:@selector(onClickSelf) forControlEvents:UIControlEventTouchUpInside];
        [_mainScrollView addSubview:btn];
    }
    
}

- (void)onClickSelf
{
    [[NSUserDefaults standardUserDefaults] setBool:YES forKey:@"isFirst"];
    [[NSUserDefaults standardUserDefaults] synchronize];
    YSGWebViewController *vc = [[YSGWebViewController alloc] init];
    [[[UIApplication sharedApplication] delegate] window].rootViewController = vc;
    [[[[UIApplication sharedApplication] delegate] window].window makeKeyAndVisible];
}

@end
