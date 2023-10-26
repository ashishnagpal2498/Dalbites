import React, { useState } from "react";
import {
  View,
  Text,
  ScrollView,
  Image,
  TouchableOpacity,
  FlatList,
  StyleSheet,
  Button,
} from "react-native";
import tw from "twrnc";
const RestaurantDetails = () => {
  const [activeTab, setActiveTab] = useState("items");

  const restaurant = {
    id: 1,
    name: "Sample Restaurant Nae",
    description:
      "The Restaurant card size will increase with repect to the length of the description",
    image: require("../assets/images/placeholder.png"),
  };

  const itemData = [
    {
      id: 1,
      name: "Item 1",
      price: "$10",
      preparationTime: "20 mins",
      description:
        "This description length will be fixed.",
      image: require("../assets/images/Placeholder_Food_Item.png"),
    },
    {
      id: 2,
      name: "Item 2",
      price: "$15",
      preparationTime: "25 mins",
      description: "A delicious item from our menu.",
      image: require("../assets/images/Placeholder_Food_Item.png"),
    },
    {
      id: 3,
      name: "Item 3",
      price: "$15",
      preparationTime: "25 mins",
      description: "A delicious item from our menu.",
      image: require("../assets/images/Placeholder_Food_Item.png"),
    },
  ];

  const reviewData = [
    {
      id: 1,
      name: "John Doe",
      date: "October 10, 2023",
      rating: "4.5",
      review: "If Required The Review UI will be improved in later stages ",
    },
    {
      id: 2,
      name: "Jane Smith",
      date: "October 12, 2023",
      rating: "4.5",
      review: "I had a wonderful dining experience here.",
    },
    {
      id: 3,
      name: "wdcwcwcw",
      date: "October 12, 2023",
      rating: "4.5",
      review: "I had a wonderful dining experience here.",
    },
    {
      id: 4,
      name: "wcvwv",
      date: "October 12, 2023",
      rating: "4.5",
      review: "I had a wonderful dining experience here.",
    },
    {
      id: 5,
      name: "nk kr lvle",
      date: "October 12, 2023",
      rating: "4.5",
      review: "I had a wonderful dining experience here.",
    },
  ];

  return (
    <View style={styles.container}>
      <View style={styles.header}>
        <View style={styles.extraheader}>
          <Text style={styles.restaurantName}>{restaurant.name}</Text>
        </View>
      </View>

      <View style={styles.restaurantCard}>
        <View style={styles.restaurantImageContainer}>
          <Image source={restaurant.image} style={styles.restaurantImage} />
        </View>
        <View style={styles.restaurantDescriptionContainer}>
          <Text style={styles.restaurantDescription}>{restaurant.description}</Text>
        </View>
      </View>

      <View style={styles.tabs}>
        <TouchableOpacity
          style={[
            activeTab === "items" ? styles.activeTab : styles.nonactiveTab,
          ]}
          onPress={() => setActiveTab("items")}
        >
          <Text style={styles.TabText}>Restaurant Items</Text>
        </TouchableOpacity>

        <TouchableOpacity
          style={[
            activeTab === "reviews" ? styles.activeTab : styles.nonactiveTab,
          ]}
          onPress={() => setActiveTab("reviews")}
        >
          <Text style={styles.TabText}>Restaurant Reviews</Text>
        </TouchableOpacity>
      </View>

      <View style={styles.content}>
  {activeTab === "items" && (
    <FlatList
      data={itemData}
      keyExtractor={(item) => item.id.toString()}
      numColumns={2} // Set the number of columns to 2
      renderItem={({ item }) => (
        <View style={styles.itemCard}>
          <View style={styles.itemImageConatainer}>
            <Image style={styles.itemImage} source={restaurant.image} />
            <View style={styles.itemTextContainer}>
              <Text style={styles.itemName}>{item.name}</Text>
              <View style={styles.itemRowContainer}>
                <Text style={styles.itemPrice}>{item.price}</Text>
                <Text style={styles.itemPreparationTime}>{item.preparationTime}</Text>
              </View>
            </View>
          </View>
          <ScrollView style={styles.itemDescriptionConatainer}>
          <Text style={styles.itemDescription}>{item.description}</Text>
          </ScrollView>
          <TouchableOpacity style={styles.itemButtonAddToCart}><Text style={styles.addToCart}>Add to Cart</Text></TouchableOpacity>
          </View>
      )}
    />
  )}
  {activeTab === "reviews" && (
    <FlatList
      data={reviewData}
      keyExtractor={(item) => item.id.toString()}
      renderItem={({ item }) => (
        <View style={styles.reviewCard}>
          <Text style={styles.reviewName}>{item.name}</Text>
          <Text style={styles.reviewDate}>{item.date}</Text>
          <Text style={styles.reviewRating}>Rating: {item.rating}</Text>
          <Text style={styles.reviewText}>{item.review}</Text>
        </View>
      )}
    />
  )}
</View>


    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    flexDirection: "column",
    justifyContent: "flex-start",
  },
  header: {
    padding: 10,
    backgroundColor: "white",
    margin: 7,
    marginRight: 5,
    elevation: 5,
  },
  extraheader:{
    backgroundColor: "#EAB308",
    padding: 7,
    borderRadius: 10,
  },
  restaurantName: {
    fontSize: 20,
    fontWeight: "bold",
    textAlign:'center',
  },
  restaurantCard: {
    height: 'auto',
    backgroundColor: "#FFFFFF",
    borderRadius: 10,
    elevation: 5,
    marginLeft: 10,
    marginRight: 8,
  },
  restaurantImageContainer:{
    width: "100%",
    height: 150,

    borderTopLeftRadius: 10,
    borderTopRightRadius: 10,
  },
  restaurantImage: {
    width: "100%",
    height: 150,
    borderTopLeftRadius: 10,
    borderTopRightRadius: 10,
  },
  restaurantDescriptionContainer: {
    flexDirection: "row",
    width: "100%",
    padding: 7,
  },
  restaurantDescription: {
    fontSize: 14,
    color: "#000",
    textAlign: "left",
    flex: 1,
  },
  tabs: {
    marginVertical: 5,
    display: "flex",
    flexDirection: "row",
    justifyContent: "space-between",
    padding: 7,
    backgroundColor: "#fff",
    elevation: 5,
    width: "100%",
  },
  nonactiveTab: {
    backgroundColor: "#ccc",
    padding: 7,
    borderRadius: 10,
    width: "48%",
  },
  activeTab: {
    backgroundColor: "#EAB308",
    padding: 7,
    borderRadius: 10,
    width: "48%",
  },
  TabText: {
    color: "black",
    textAlign: "center",
    fontSize: 15,
    fontWeight: "bold",
  },
  content: {
    flex:1,
    padding: 5,
    alignItems:'center',
  },
  itemCard: {
    height: 250,
    width:160,
    backgroundColor: "#FFFFFF",
    borderRadius: 10,
    elevation: 5,
    margin: 5,
    padding: 7,
    alignSelf:'center',
  },
  itemImageConatainer: {
    height: 150,
    borderTopLeftRadius: 10,
    borderTopRightRadius: 10,
  },
  itemImage: {
    width: "100%",
    height: 150,
    borderTopLeftRadius: 10,
    borderTopRightRadius: 10,
  },
  itemTextContainer: {
    width: "100%",
    position: "absolute",
    bottom: 0,
  },
  itemName: {
    fontSize: 20,
    fontWeight: "bold",
    paddingHorizontal: 10,
    color: "#EAB308",
    backgroundColor: "rgba(0, 0, 0, 0.5)",
    textAlign: "left",
  },
  itemRowContainer: {
    flexDirection: "row",
    justifyContent: "space-between",
    paddingHorizontal: 10,
    paddingBottom: 5,
    backgroundColor: "rgba(0, 0, 0, 0.5)",
  },
  itemPrice: {
    fontSize: 16,
    color: "#FFFFFF",
    textAlign: "center",
  },
  itemPreparationTime: {
    fontSize: 16,
    color: "#FFFFFF",
    textAlign: "center",
  },
  itemDescriptionConatainer: {
    
  },
  itemDescription: {
    fontSize: 14,
    color: "#000",
    textAlign: "left",
  },
  itemButtonAddToCart: {
    backgroundColor: "#EAB308",
    padding: 7,
    width:140,
    borderRadius: 10,
    alignSelf:'center',
  },
  addToCart: {
    color: "black",
    textAlign: "center",
    fontSize: 15,
    fontWeight: "bold",
    textAlignVertical: "center",
  },

  reviewCard: {
    height: "auto",
    backgroundColor: "#FFFFFF",
    borderRadius: 10,
    elevation: 5,
    margin: 5,
    padding: 7,
  },
  reviewName: {
    fontSize: 16,
    fontWeight: "bold",
  },
  reviewDate: {
    fontSize: 14,
  },
  reviewRating:{
    fontSize: 14,
  },
  reviewText: {
    fontSize: 14,
  },
});
export default RestaurantDetails;
