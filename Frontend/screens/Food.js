import React from "react";
import { SafeAreaView, Text, Pressable, StyleSheet } from "react-native";

const Food = () => {
  return (
    <SafeAreaView style={styles.container}>
      <Text>Food Page</Text>
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  container: {
    marginTop: 25,
  },
});

export default Food;
