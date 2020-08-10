import React, { Component } from 'react'
import { View, StyleSheet, Dimensions, BackHandler } from 'react-native'
import {inject, observer} from 'mobx-react'

import Carousel from 'react-native-snap-carousel';
import PurposeNameWrite from './PurposeNameWrite';
import PurposeDescriptionWrite from './PurposeDescriptionWrite';
import PurposeThumbnailWrite from './PurposeThumbnailWrite';
import PurposeDecimalDayWrite from './PurposeDecimalDayWrite';
import PurposeDetailPlansWrite from './PurposeDetailPlansWrite';
import PurposeOtherWrite from './PurposeOtherWrite';
import PurposeWriteDone from './PurposeWriteDone';

import ImageButton from '../../../atom/button/ImageButton'
import PageStateText from '../../../atom/text/PageStateText'


const fullWidth = Dimensions.get('window').width;

@inject(['purposeWriteStore'])
@observer
export default class PurposeWriteBoard extends Component {

    constructor(props) {
        super(props)

        this.views = [
            <PurposeNameWrite next={this._next}/>,
            <PurposeDescriptionWrite next={this._next}/>,
            <PurposeThumbnailWrite/>,
            <PurposeDecimalDayWrite/>,
            <PurposeDetailPlansWrite navigation={this.props.navigation} />,
            <PurposeOtherWrite/>,
            <PurposeWriteDone navigation={this.props.navigation} />
        ]

        this.purposeWriteStore = this.props.purposeWriteStore
        this.purposeWriteStore.start(this.views.length);
    }

    _renderItem = ({ item, index }) => {
        return (
            item
        );
    }

    _next = () => {
        
        if(this.purposeWriteStore.hasNext){
            this.purposeWriteStore.next(this.carousel);
        }
    }

    _previous = () => {
        if(this.purposeWriteStore.hasPrevious){
            this.purposeWriteStore.previous(this.carousel);
        }
    }

    componentWillMount() {
        BackHandler.addEventListener('hardwareBackPress', () => {
            if (this.purposeWriteStore.activeIndex != 0) {
                this._previous();
                return true;
            } else {
                return false;
            }
        })
    }
    
    render() {
        return (
            <View style={{ flex: 1, backgroundColor: '#ffffff'}}>
                <View style={styles.topContainer}>
                    {/* <PageStateText activeIndex={this.state.activeIndex + 1} endIndex={this.state.endIndex}/> */}
                    <ImageButton
                        source={this.purposeWriteStore.hasPrevious ? 
                            require('../../../../../../asset/button/exit_button.png') :
                            require('../../../../../../asset/button/exit_button.png')
                        }
                        width={40}
                        height={40}
                        onPress={() => {
                            if(this.purposeWriteStore.hasPrevious){
                                this._previous();
                            }else{
                                this.props.navigation.goBack();
                            }
                        }}
                    />
                </View>
                <View style={styles.viewContainer}>
                    <Carousel
                        style={{ flex: 1 }}
                        ref={ref => this.carousel = ref}
                        data={this.views}
                        renderItem={this._renderItem}
                        scrollEnabled={false}
                        sliderWidth={fullWidth}
                        itemWidth={fullWidth}
                    />
                </View>
                <View style={styles.bottomContainer}>
                    <View style={{ width: "30%" }}>
                    </View>
                    <View style={{ width: "40%", alignItems: 'center' }}>
                        <PageStateText activeIndex={this.purposeWriteStore.activeIndex + 1} endIndex={this.purposeWriteStore.endIndex} />
                    </View>
                    <View style={{ width: "30%", alignItems: 'flex-end', paddingRight: 12 }}>
                        {this.purposeWriteStore.hasNext &&
                            <ImageButton
                                source={this.purposeWriteStore.isPermitNextScene ? 
                                    require('../../../../../../asset/button/active_next_button.png') :
                                    require('../../../../../../asset/button/inactive_next_button.png')
                                }
                                width={60}
                                height={60}
                                disabled={!this.purposeWriteStore.isPermitNextScene}
                                onPress={this._next}/>
                        }
                    </View>
                </View>
            </View>
        );
    }
}

const styles = StyleSheet.create({
    topContainer: {
        flex: 0.6,
        alignItems: 'flex-start',
        justifyContent: 'center',
        paddingLeft: 7.5

    },
    viewContainer: {
        flex: 6
    },
    bottomContainer: {
        flex: 1,
        flexDirection: 'row',
        alignItems: "center"
    },

    indexN: {
        fontSize: 20,
    },


})