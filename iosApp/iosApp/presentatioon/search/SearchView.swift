//
//  SearchView.swift
//  iosApp
//
//  Created by Ian  Arbuckle on 06/06/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI

struct SearchView: View {
    var body: some View {
        NavigationView {
            Text("Search")
            .navigationBarTitle(Text("Search"))
        }
    }
}

struct SearchView_Previews: PreviewProvider {
    static var previews: some View {
        SearchView()
    }
}
