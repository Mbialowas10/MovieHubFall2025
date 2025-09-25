package com.mbialowas.moviehubfall2025.destinations

open class Destination(val route:String) {
    /**
     * Purpose: A central place to manage routing information.
     */
    object Movie: Destination("movie")
    object Search : Destination("search")
    object Watch : Destination("watch")
}