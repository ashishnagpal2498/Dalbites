import React from "react";
import { createBottomTabNavigator } from "@react-navigation/bottom-tabs";
import IconTextBar from "../Layouts/IconTextBar";
import RestaurantStack from "./RestaurantStack";
import Cart from "../../screens/Cart";
import Profile from "../../screens/Profile"

const Tab = createBottomTabNavigator();

const HomeTabGroup = () => (
  <Tab.Navigator
    screenOptions={({ route }) => ({
      headerShown: false,
      tabBarIcon: ({ focused, color }) => {
        let iconName;
        if (route.name === "Profile") {
          iconType = "Mci";
          iconName = "account";
        } else if (route.name === "Restaurants") {
          iconType = "Ini";
          iconName = "restaurant";
        } else if (route.name === "Cart") {
          iconType = "Ini";
          iconName = focused
            ? "cart"
            : "cart-outline";
        }

        return (
          <IconTextBar
            iconName={iconName}
            iconType={iconType}
            iconOnly={true}
            color={color}
          />
        );
      },
      tabBarActiveTintColor: "tomato",
      tabBarInactiveTintColor: "gray",
    })}
  >
    <Tab.Screen name="Restaurants" component={RestaurantStack} />
    <Tab.Screen name="Profile" component={Profile} />
    <Tab.Screen name="Cart" component={Cart} />
  </Tab.Navigator>
);

export default HomeTabGroup;
