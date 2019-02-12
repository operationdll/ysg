//
//  JSInterface.h
//  YSG
//
//  Created by Apple on 2017/6/29.
//  Copyright © 2017年 Apple. All rights reserved.
//


#import <Foundation/Foundation.h>
#import <webkit/webkit.h>

extern const NSString *EventHandler;

@interface JSInterface : NSObject<WKScriptMessageHandler>

@property (nonatomic, weak) WKWebView *webView;
@property (nonatomic, strong) NSString  *handlerJS;

+ (instancetype)shareInstance;


+ (void)getInject:(WKWebView *)webView;

@end
