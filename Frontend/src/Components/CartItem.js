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
        <Image
          src={cardData.menu_image}
          style={styles.menuItemImage}
        />
        <View style={styles.itemDetailsHolder}>
          <Text style={styles.title}>{cardData.name}</Text>
          <Text style={styles.price}>Price: ${cardData.price}</Text>
          <View style={styles.quantityContainer}>
            <TouchableOpacity style={tw`bg-yellow-500 rounded-lg py-2 px-4`} onPress={decreaseQuantity}>
              <Text style={tw`text-black text-lg font-semibold`}>-</Text>
            </TouchableOpacity>
            <Text style={styles.quantity}>{quantity}</Text>
            <TouchableOpacity style={tw`bg-yellow-500 rounded-lg py-2 px-4`} onPress={increaseQuantity}>
              <Text style={tw`text-black text-lg font-semibold`}>+</Text>
            </TouchableOpacity>
          </View>
        </View>
      </View>
      <TouchableOpacity style={tw`bg-yellow-500 py-2 px-4`} onPress={removeFromCart}>
        <Text style={tw`text-black text-lg font-semibold text-center justify-center content-center`}>Remove from Cart</Text>
      </TouchableOpacity>
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
    title: {
      fontSize: 20,
      fontWeight: 'bold',
      marginBottom: 8,
      paddingHorizontal: 16,
      paddingTop: 8,
    },
    description: {
      fontSize: 16,
      marginBottom: 8,
      paddingHorizontal: 16,
    },
    price: {
      fontSize: 18,
      fontWeight: 'bold',
      marginBottom: 8,
      paddingHorizontal: 16,
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
    menuItemImage: {
      width: '50%',
      maxHeight: 150
    },
    itemHolder: {
      display: 'flex',
      flexDirection: 'row'
    },
    itemDetailsHolder: {
      width: '50%',
    }
  });

export default CartItem;