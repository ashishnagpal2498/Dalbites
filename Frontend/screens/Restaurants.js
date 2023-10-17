import React from "react";
import { View, Text, StyleSheet, Pressable } from "react-native";

const Restaurants = () => {
  return (
    <View style={styles.container}>
      <Text>This is restaurant Page</Text>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    marginTop: 25,
  },
});

export default Restaurants;
