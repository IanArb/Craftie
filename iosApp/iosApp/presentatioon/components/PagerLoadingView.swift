//
//  PagerLoadingView.swift
//  iosApp
//
//  Created by Ian Arbuckle on 01/03/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct PagerLoadingView: View {
    var perform: () -> Void
    var body: some View {
            HStack {
                Spacer()
                VStack {
                    ProgressView()
                    Text("Loading next page...")
                }
                Spacer()
            }
            .onAppear(perform: {
                perform()
            })
        }
}

struct PagerLoadingView_Previews: PreviewProvider {
    static var previews: some View {
        PagerLoadingView {
            
        }
    }
}
