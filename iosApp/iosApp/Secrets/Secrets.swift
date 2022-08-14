//
//  Secrets.swift
//  iosApp
//
//  Created by Ian Arbuckle on 14/08/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation

struct Secrets: Decodable {
    let username: String
    let password: String
    
    static func loadJson() throws -> Self {
        let file = Bundle.main.url(forResource: "secrets", withExtension: "json")
                
        guard let secretsFileUrl = file, let secretsFileData = try? Data(contentsOf: secretsFileUrl) else {
                  fatalError("No `secrets.json` file found. Make sure to duplicate `secrets.json.sample` and remove the `.sample` extension.")
                }

        return try JSONDecoder().decode(Self.self, from: secretsFileData)
    }
}
