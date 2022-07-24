//
//  BaseUrlProvider.swift
//  iosApp
//
//  Created by Ian Arbuckle on 12/06/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation

class BaseUrlProvider {
    
    private let PRODUCTION_URL = "http://craftie.app:8080/api/v1"
    private let STAGING_URL = "https://craftie-api.herokuapp.com/api/v1"
    
    func resolveUrl() -> String {
        guard let filePath = Bundle.main.path(forResource: "CraftieAPI-Info", ofType: "plist") else {
          fatalError("Couldn't find file 'CraftieAPI-Info.plist")
        }
        
        let plist = NSDictionary(contentsOfFile: filePath)
        guard let value = plist?.value(forKey: "ENABLE_PRODUCTION") as? Bool else {
            return "Couldn't find key in 'CraftieAPI-Info.plist"
        }
        
        if (value == true) {
            return PRODUCTION_URL
        } else {
            return STAGING_URL
        }
    }
}
