//
//  UsernamePasswordProvider.swift
//  iosApp
//
//  Created by Ian Arbuckle on 17/04/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation

class UsernamePasswordProvider {
    
    func username() -> String {
        guard let filePath = Bundle.main.path(forResource: "CraftieAPI-Info", ofType: "plist") else {
          fatalError("Couldn't find file 'CraftieAPI-Info.plist'.")
        }
        
        let plist = NSDictionary(contentsOfFile: filePath)
        guard let value = plist?.object(forKey: "USERNAME") as? String else {
          fatalError("Couldn't find key 'username' in 'CraftieAPI-Info.plist'.")
        }
        
        if (value.starts(with: "_")) {
              fatalError("Please register to gain access to the API token")
        }
        
        return value
    }
    
    func password() -> String {
        guard let filePath = Bundle.main.path(forResource: "CraftieAPI-Info", ofType: "plist") else {
          fatalError("Couldn't find file 'CraftieAPI-Info.plist'.")
        }
     
        let plist = NSDictionary(contentsOfFile: filePath)
        guard let value = plist?.object(forKey: "PASSWORD") as? String else {
          fatalError("Couldn't find key 'password' in 'CraftieAPI-Info.plist'.")
        }
        
        if (value.starts(with: "_")) {
              fatalError("Please register to gain access to the API token")
        }
        
        return value
    }
    
}
