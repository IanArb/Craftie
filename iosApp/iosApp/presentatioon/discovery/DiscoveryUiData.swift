//
//  DiscoveryUiData.swift
//  iosApp
//
//  Created by Ian  Arbuckle on 06/06/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import shared

struct DiscoveryUiData {
    var beers: [Beer]
    var breweries: [Brewery]
    var featuredBeer: Beer
    var provinces: [Province]
}

struct ProvinceLocal {
    var imageUrl: String
    var name: String
}
