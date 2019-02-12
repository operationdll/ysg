//
//  YXLocationManager.m
//  YSG
//
//  Created by Apple on 2017/6/24.
//  Copyright © 2017年 Apple. All rights reserved.
//

#import "YXLocationManager.h"
#import <CoreLocation/CoreLocation.h>
#import <UIKit/UIKit.h>
@interface YXLocationManager ()<CLLocationManagerDelegate>
@property (nonatomic, strong) CLLocationManager * manager;
@property (copy, nonatomic)  void(^LocationBack)(NSString *lat,NSString *lng, NSString *name);
@end

@implementation YXLocationManager

+ (YXLocationManager *)sharedManager
{
    static YXLocationManager *sharedManagerInstance = nil;
    static dispatch_once_t predicate; dispatch_once(&predicate, ^{
        sharedManagerInstance = [[self alloc] init];
    });
    if (!sharedManagerInstance.LocationBack) {
        [sharedManagerInstance setPostion];
    }
    return sharedManagerInstance;
}

- (void)setPostion{
    self.manager = [[CLLocationManager alloc] init];
    double version = [[[UIDevice currentDevice] systemVersion] doubleValue];
    // iOS8之后 再获取用户位置信息 需要用户进行授权
    if (version >= 8.0) {
        [self.manager requestAlwaysAuthorization];
        [self.manager requestWhenInUseAuthorization];
    }
    
    // 获得位置信息时 设置代理
    self.manager.delegate = self;
    
    // 设置距离过滤器 移动百米之后 再重新进行定位
    self.manager.distanceFilter = 100;
    
    // 设置定位精确度 设定的精确度越高 手机耗电量越大
    /*
     kCLLocationAccuracyBestForNavigation 导航定位(最精确的)
     kCLLocationAccuracyBest; 精确定位
     kCLLocationAccuracyNearestTenMeters; 10米误差
     kCLLocationAccuracyHundredMeters; 百米误差
     kCLLocationAccuracyKilometer; 千米误差
     kCLLocationAccuracyThreeKilometers; 3千米误差
     */
    self.manager.desiredAccuracy = kCLLocationAccuracyBestForNavigation;
    [self.manager startUpdatingLocation];
    
}

- (void)getLocationCallBack:(void (^)(NSString *, NSString *, NSString *))locationback{
    if (locationback) {
        self.LocationBack = locationback;
    }
    [self setPostion];
}

- (void)locationManager:(CLLocationManager *)manager didUpdateLocations:(NSArray *)locations{
    CLLocation * location = [locations lastObject];
    CLLocationCoordinate2D coordinate = location.coordinate;
    CLLocationDegrees latitude = coordinate.latitude;
    CLLocationDegrees longitude = coordinate.longitude;
    if (self.LocationBack) {
        self.LocationBack([NSString stringWithFormat:@"%.2f",latitude],[NSString stringWithFormat:@"%.2f",longitude],@"");
    }
    if (self.LocationDict) {
        self.LocationDict([NSString stringWithFormat:@"%.2f",latitude], [NSString stringWithFormat:@"%.2f",longitude]);
    }
    CLGeocoder *clGeoCoder = [[CLGeocoder alloc] init];
    [clGeoCoder reverseGeocodeLocation:location completionHandler: ^(NSArray *placemarks,NSError *error) {
        for (CLPlacemark *placeMark in placemarks)
        {
            NSDictionary *addressDic=placeMark.addressDictionary;
            
            NSString *state=[addressDic objectForKey:@"State"];
            NSString *city=[addressDic objectForKey:@"City"];
            NSString *subLocality=[addressDic objectForKey:@"SubLocality"];
            NSString *street=[addressDic objectForKey:@"Street"];
            NSLog(@"%@",street);
            if (self.LocationName) {
                self.LocationName(state, city, subLocality);
            }
        }
        
    }];
}

@end
