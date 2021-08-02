//
//  SearchView.swift
//  iosApp
//
//  Created by Ian  Arbuckle on 06/06/2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI

struct SearchView: View {
    
    @State private var searchText = ""
    
    var body: some View {
        NavigationView {
            
            .navigationBarTitle(Text("Search"))
        }
        .background(Color(red: 248, green: 248, blue: 248))
    }
}

struct SearchView_Previews: PreviewProvider {
    static var previews: some View {
        SearchView()
    }
}
