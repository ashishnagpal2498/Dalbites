import React from 'react';
import { View, TouchableOpacity, Text } from 'react-native';
import { MultipleSelectList, SelectList  } from 'react-native-dropdown-select-list'
import tw from "twrnc";

const Filter = ({navigation}) => {

    const buildingList = [
        {key:'1', value:'Goldberg'},
        {key:'2', value:'Killam'},
        {key:'3', value:'DSU'},
        {key:'4', value:'Kenneth'},
    ];

    const sortByList = [
        {key: '1', value: 'Rating'},
        {key: '2', value: 'Price - High to Low'},
        {key: '3', value: 'Price - Low to High'},
        {key: '4', value: 'Preparation Time - High to Low'},
        {key: '5', value: 'Preparation Time - Low to High'},
    ]

    const [buildings, setBuildings] = React.useState([]);
    const [sortBy, setSortBy] = React.useState("");

    const handleFilter = () => {
        console.log(buildings);
        console.log(sortBy);
        navigation.navigate("Restaurants");
    };
    
    return(
    <View>
        {/* <View style={{width: '80%', marginLeft: '10px'}}> */}
        <View style={tw`px-7`}>
            <MultipleSelectList 
                setSelected={(val) => setBuildings(val)} 
                data={buildingList} 
                save="value"
                boxStyles={{marginTop:25}}
                label="Buildings"
                placeholder="Select Buildings"
                searchPlaceholder="Search"
            />
        </View>
        {/* <View style={{marginTop: '15px', width: '80%', marginLeft: '10px'}}> */}
        <View style={tw`px-7 py-4`}>
            <SelectList 
                setSelected={(val) => setSortBy(val)} 
                data={sortByList} 
                save="value"
                label="Sort by"
                placeholder="Sort by"
                searchPlaceholder="Search"
                search={false} 
            />
        </View>
        <TouchableOpacity
        style={tw`bg-yellow-500 rounded-lg py-2 mt-15 ml-33 w-1/4`}
        onPress={handleFilter}
        >
            <Text style={tw`text-black text-lg font-semibold text-center`}>Apply</Text>
        </TouchableOpacity>
    </View>
  )
};

export default Filter;