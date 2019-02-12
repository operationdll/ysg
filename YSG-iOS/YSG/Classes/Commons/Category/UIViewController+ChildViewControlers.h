//
//  UIViewController+ChildViewControlers.h
//  FDLM
//
//  Created by mac on 15/8/10.
//  Copyright (c) 2015年 WangtianSoft. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface UIViewController (ChildViewControlers)

- (void)containerAddChildViewController:(UIViewController *)childViewController parentView:(UIView *)view;

- (void)containerAddChildViewController:(UIViewController *)childViewController toContainerView:(UIView *)view useAutolayout:(BOOL)autolayout;

- (void)containerAddChildViewController:(UIViewController *)childViewController toContainerView:(UIView *)view;

- (void)containerAddChildViewController:(UIViewController *)childViewController;

- (void)containerRemoveChildViewController:(UIViewController *)childViewController;



@end
