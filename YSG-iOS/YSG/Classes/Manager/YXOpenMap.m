//
//  YXOpenMap.m
//  YSG
//
//  Created by Apple on 2017/6/24.
//  Copyright © 2017年 Apple. All rights reserved.
//

#import "YXOpenMap.h"
#import <MapKit/MapKit.h>
#import <CoreLocation/CoreLocation.h>
@implementation YXOpenMap

+(void)openMapsLat:(float)lat Ing:(float)ing Vc:(UIViewController *)vc{
    UIAlertController *alertVC = [UIAlertController alertControllerWithTitle:@"选择地图" message:nil preferredStyle:UIAlertControllerStyleActionSheet];
    CLLocationCoordinate2D cll;
    cll.latitude = lat;
    cll.longitude = ing;
    for (NSMutableDictionary *dict in [self getInstalledMapAppWithEndLocation:cll]) {
        UIAlertAction *action = [UIAlertAction actionWithTitle:dict[@"title"] style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action) {
            if ([dict[@"title"] isEqualToString: @"苹果地图"]) {
                [self navAppleMapEndLocation:cll];
            }else{
                [[UIApplication sharedApplication] openURL:[NSURL URLWithString:dict[@"url"]]];
            }
        }];
        [alertVC addAction:action];
    }
    UIAlertAction *cancle = [UIAlertAction actionWithTitle:@"取消" style:UIAlertActionStyleCancel handler:nil];
    [alertVC addAction:cancle];
    [vc presentViewController:alertVC animated:YES completion:nil];
}

#pragma mark LCActionSheetDelegate


//苹果地图
+ (void)navAppleMapEndLocation:(CLLocationCoordinate2D)endLocation
{
    MKMapItem *currentLoc = [MKMapItem mapItemForCurrentLocation];
    MKMapItem *toLocation = [[MKMapItem alloc] initWithPlacemark:[[MKPlacemark alloc] initWithCoordinate:endLocation addressDictionary:nil]];
    NSArray *items = @[currentLoc,toLocation];
    NSDictionary *dic = @{
                          MKLaunchOptionsDirectionsModeKey : MKLaunchOptionsDirectionsModeDriving,
                          MKLaunchOptionsMapTypeKey : @(MKMapTypeStandard),
                          MKLaunchOptionsShowsTrafficKey : @(YES)
                          };
    
    [MKMapItem openMapsWithItems:items launchOptions:dic];
}

#pragma mark - 导航方法
+ (NSArray *)getInstalledMapAppWithEndLocation:(CLLocationCoordinate2D)endLocation
{
    NSMutableArray *maps = [NSMutableArray array];
    
    //苹果地图
    NSMutableDictionary *iosMapDic = [NSMutableDictionary dictionary];
    iosMapDic[@"title"] = @"苹果地图";
    [maps addObject:iosMapDic];
    
    //百度地图
    if ([[UIApplication sharedApplication] canOpenURL:[NSURL URLWithString:@"baidumap://"]]) {
        NSMutableDictionary *baiduMapDic = [NSMutableDictionary dictionary];
        baiduMapDic[@"title"] = @"百度地图";
        NSString *urlString = [[NSString stringWithFormat:@"baidumap://map/direction?origin={{我的位置}}&destination=latlng:%f,%f|name=北京&mode=driving&coord_type=gcj02",endLocation.latitude,endLocation.longitude] stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding];
        baiduMapDic[@"url"] = urlString;
        [maps addObject:baiduMapDic];
    }
    
    //高德地图
    if ([[UIApplication sharedApplication] canOpenURL:[NSURL URLWithString:@"iosamap://"]]) {
        NSMutableDictionary *gaodeMapDic = [NSMutableDictionary dictionary];
        gaodeMapDic[@"title"] = @"高德地图";
        NSString *urlString = [[NSString stringWithFormat:@"iosamap://navi?sourceApplication=%@&backScheme=%@&lat=%f&lon=%f&dev=0&style=2",@"导航功能",@"nav123456",endLocation.latitude,endLocation.longitude] stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding];
        gaodeMapDic[@"url"] = urlString;
        [maps addObject:gaodeMapDic];
    }
    
    //腾讯地图
    if ([[UIApplication sharedApplication] canOpenURL:[NSURL URLWithString:@"qqmap://"]]) {
        NSMutableDictionary *qqMapDic = [NSMutableDictionary dictionary];
        qqMapDic[@"title"] = @"腾讯地图";
        NSString *urlString = [[NSString stringWithFormat:@"qqmap://map/routeplan?from=我的位置&type=drive&tocoord=%f,%f&to=终点&coord_type=1&policy=0",endLocation.latitude, endLocation.longitude] stringByAddingPercentEscapesUsingEncoding:NSUTF8StringEncoding];
        qqMapDic[@"url"] = urlString;
        [maps addObject:qqMapDic];
    }
    
    return maps;
}

@end

