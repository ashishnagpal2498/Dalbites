import React from "react";
import { View, Text, StyleSheet, TouchableOpacity,Image } from "react-native";

const RestaurantCard = ({ item }) => (
    <TouchableOpacity>
      <View style={styles.card}>
        <View style={styles.imageConatainer}>
          <Image style={styles.image} source={item.image} />
          <View style={styles.textContainer}>
            <Text style={styles.restaurantName}>{item.name}</Text>
            <View style={styles.rowContainer}>
              <Text style={styles.buildingName}>{item.building}</Text>
              <Text style={styles.rating}>Rating: {item.rating}</Text>
            </View>
          </View>
        </View>
        <View style={styles.moreDetailsContainer}>
          <Text style={styles.bestSellerText}>Best Seller: {item.bestSeller}</Text>
          <Text style={styles.timeText}>Avg Prep Time: {item.avgpreptime}</Text>
        </View>
      </View>
    </TouchableOpacity>
  );

const styles = StyleSheet.create({
    card: {
        width: "100%",
        height: 190,
        backgroundColor: "#FFFFFF",
        borderRadius: 10,
        elevation: 5,
        marginBottom:10
      },
      imageConatainer:{
        width: "100%",
        height: 150,
        borderTopLeftRadius: 10,
        borderTopRightRadius: 10,  
    
      },
      image: {
        width: "100%",
        height: 150,
        borderTopLeftRadius: 10,
        borderTopRightRadius: 10,
      },
      textContainer: {
        width:"100%",
        position: "absolute",
        bottom: 0,
      },
      restaurantName: {
        fontSize: 20,
        fontWeight: "bold",
        paddingHorizontal:10,
        color: "#ecc94b",
        backgroundColor: 'rgba(0, 0, 0, 0.5)',
        textAlign: 'left',
      },
      rowContainer: {
        flexDirection: "row",
        justifyContent: "space-between",
        paddingHorizontal:10,
        paddingBottom: 5,
        backgroundColor: 'rgba(0, 0, 0, 0.5)',
      },
      buildingName: {
        fontSize: 16,
        color: "#FFFFFF",
        textAlign: 'center',
      },
      rating: {
        fontSize: 16,
        color: "#FFFFFF",
        textAlign: 'center',
      },
      moreDetailsContainer: {
        width:"100%",
        position: "absolute",
        bottom: 0,
        flexDirection: "row",
        justifyContent: "space-between",
        padding:10,
      },
      bestSellerText: {
        fontSize: 14,
        color: "#000",
      },
      timeText: {
        fontSize: 14,
        color: "#000",
      },
});

export default RestaurantCard;
