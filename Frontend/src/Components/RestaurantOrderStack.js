import React from 'react'
import {createStackNavigator} from '@react-navigation/stack'

import ReceivedOrder from '../../screens/ReceivedOrder'

const Stack = createStackNavigator()

const RestaurantOrderStack = () => {
  const screenOptions = {
    headerShown: null,
  }
  return (
    <Stack.Navigator screenOptions={screenOptions}>
      <Stack.Screen name="ReceivedOrder" component={ReceivedOrder} />
    </Stack.Navigator>
  )
}

export default RestaurantOrderStack
