import * as React from 'react';
import { useState, useEffect } from 'react';
import { Dimensions, Image, StyleSheet, Text, TouchableOpacity, View } from 'react-native';
import { useDispatch } from 'react-redux';
import tw from "twrnc";
import { deleteCartItem, updateCartItem } from '../../redux/actions/CartAction';

const CartItem = ({cardData}) => {
  const [quantity, setQuantity] = useState(cardData.quantity || 1);
  const dispatch = useDispatch();

  const increaseQuantity = () => {
    if (quantity < 15) {
      setQuantity(quantity + 1);
      dispatch(updateCartItem({item: { ...cardData, quantity: quantity + 1 }}))
    }
  };
  
  const decreaseQuantity = () => {
    if (quantity > 1) {
      setQuantity(quantity - 1);
      dispatch(updateCartItem({ item:{ ...cardData, quantity: quantity - 1 }}))
    }
  };
  
  const removeFromCart = () => {
    dispatch(deleteCartItem({ id: cardData.id }))
  };

  useEffect(() => {
    setQuantity(cardData.quantity || 1)
  }, [cardData.quantity])

  return (
    <View style={styles.card}>

      <View style={styles.itemHolder}>
        <View style={styles.itemImageContainer}>
          <Image
            src={cardData.menu_image}
            style={styles.menuItemImage} 
          />
        </View>
        <View style={styles.itemDetailsHolder}>
          <Text style={styles.itemName}>{cardData.name}</Text>
          <Text style={styles.orderCardText}>Cost: ${" "}{cardData.price}</Text>
          <Text style={styles.orderCardText}>
          Total Cost: ${" "}{Number(cardData.price * cardData.quantity).toPrecision(4)}
          </Text>
        </View>
      </View>

      <View style={styles.buttonsContainer}>
        <TouchableOpacity style={styles.removeButton} onPress={removeFromCart}>
          <Text style={styles.removeButtonText}>Remove</Text>
        </TouchableOpacity>
        <View style={styles.quantityContainer}>
          <TouchableOpacity style={tw`bg-yellow-500 rounded-lg py-2 px-4`} onPress={decreaseQuantity}>
            <Text style={styles.ButtonText}>-</Text>
          </TouchableOpacity>
          <Text style={styles.quantity}>{quantity}</Text>
          <TouchableOpacity style={tw`bg-yellow-500 rounded-lg py-2 px-4`} onPress={increaseQuantity}>
            <Text style={styles.ButtonText}>+</Text>
          </TouchableOpacity>
        </View>
      </View>

    </View>
  );
};

const styles = StyleSheet.create({
    card: {
      marginTop: 3,
      backgroundColor: '#fff',
      borderRadius: 8,
      borderColor: 'black',
      margin: 3,
      marginBottom: 16,
      elevation: 3,
      overflow: 'hidden',
    },
    coverImage: {
      height: 200,
      width: '100%',
      resizeMode: 'cover',
    },
    itemName: {
      fontSize: 20,
      fontWeight: "bold",
    },
    orderCardText: {
      fontSize: 15,
      fontWeight: "500",
    },
    quantityContainer: {
      flexDirection: 'row',
      alignItems: 'center',
      marginBottom: 8,
      paddingHorizontal: 16,
    },
    quantity: {
      fontSize: 18,
      marginHorizontal: 8,
    },
    itemHolder: {
      flexDirection: "row",
      width: "95%",
      alignSelf: "center",
      marginVertical: 5,
    },
    itemImageContainer:{
      flex: 1.2,
      width: "100%",
      aspectRatio: 1,
      overflow: "hidden",
      borderBottomLeftRadius: 10,
      borderTopLeftRadius: 10,
      padding: 5,
    },
    menuItemImage: {
      width: "100%",
      height: "100%",
      borderRadius: 10,
    },
    itemDetailsHolder: {
      flex: 3,
      justifyContent: "space-evenly",
      padding: 5,
    },
    buttonsContainer:{
      flexDirection:"row",
      justifyContent:"space-between",
    },
    removeButton:{
      margin: 7,
      marginTop:0,
      paddingRight:9,
      borderRightWidth:2,
      width:"50%",
      alignSelf: "center",
      alignItems: "center",
    },
    removeButtonText:{
      backgroundColor: "#EAB308",
      paddingVertical: 7,
      paddingHorizontal: 15,
      borderRadius: 10,
      fontWeight:"bold",
      fontSize: 21,
    },
    ButtonText:{
      fontWeight:"bold",
      fontSize: 21,
    },
  });

export default CartItem;