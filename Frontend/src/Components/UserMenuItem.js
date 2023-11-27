import * as React from 'react';
import { useState } from 'react';
import { useDispatch } from "react-redux";
import { Image, ScrollView, StyleSheet, Text, TouchableOpacity, View , ToastAndroid } from 'react-native';
import tw from "twrnc";
import { addCartItem } from '../../redux/actions/CartAction';

const UserMenuItem = ({cardData}) => {
  const dispatch = useDispatch();
    const [quantity, setQuantity] = useState(1);
    const increaseQuantity = () => {
      if (quantity < 15) {
        setQuantity(quantity + 1);
      }
    };
    
    const decreaseQuantity = () => {
      if (quantity > 1) {
        setQuantity(quantity - 1);
      }
    };
    
  const addToCart = () => {
    dispatch(addCartItem({ item: {...cardData, quantity }}))
    ToastAndroid.show(`Item added to cart`, ToastAndroid.SHORT);
  };

    return(
        <View style={styles.itemCard}>
            <View style={styles.itemImageConatainer}>
                <Image style={styles.itemImage} src={cardData.menu_image}/>
                <View style={styles.itemTextContainer}>
                    <Text style={styles.itemName}>{cardData.name}</Text>
                    <View style={styles.itemRowContainer}>
                        <Text style={styles.itemPrice}>$ {cardData.price}</Text>
                        <Text style={styles.itemPreparationTime}>{cardData.time} mins</Text>
                    </View>
                </View>
            </View>
            <ScrollView style={styles.itemDescriptionConatainer} persistentScrollbar={true}>
                <Text style={styles.itemDescription}>{cardData.description}</Text>
            </ScrollView>
            <View style={styles.itemQuantityContainer}>
                <TouchableOpacity style={tw`bg-yellow-500 rounded-lg py-1 px-4`} onPress={decreaseQuantity}>
                    <Text style={tw`text-black text-lg font-semibold`}>-</Text>
                </TouchableOpacity>
                <Text style={styles.quantity}>{quantity}</Text>
                <TouchableOpacity style={tw`bg-yellow-500 rounded-lg py-1 px-4`} onPress={increaseQuantity}>
                    <Text style={tw`text-black text-lg font-semibold`}>+</Text>
                </TouchableOpacity>
            </View>
            <TouchableOpacity style={styles.itemButtonAddToCart} onPress={addToCart}>
                <Text style={styles.addToCart}>Add to Cart</Text>
            </TouchableOpacity>
        </View>
    );
};

const styles = StyleSheet.create({
    itemCard: {
      minHeight: 270,
      maxHeight:340,
      width: "47%",
      backgroundColor: "#FFFFFF",
      borderRadius: 10,
      elevation: 5,
      margin: 5,
      padding: 7,
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
    itemDescriptionConatainer: {},
    itemDescription: {
      fontSize: 14,
      color: "#000",
      textAlign: "left",
    },
    itemButtonAddToCart: {
      backgroundColor: "#EAB308",
      padding: 7,
      width: 140,
      borderRadius: 10,
      alignSelf: "center",
    },
    addToCart: {
      color: "black",
      textAlign: "center",
      fontSize: 15,
      fontWeight: "bold",
      textAlignVertical: "center",
    },
    itemQuantityContainer: {
        flexDirection: 'row',
        alignItems: 'center',
        marginBottom: 8,
        marginTop: 7,
        paddingHorizontal: 16,
    },
    quantity: {
        fontSize: 18,
        marginHorizontal: 8,
    },
});

export default UserMenuItem;