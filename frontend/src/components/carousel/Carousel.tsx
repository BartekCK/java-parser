import React from 'react';

// types
import { IImageIndex } from './types';
import { Side } from './types/enums';

// styles
import './styles.scss';

// assets
import JsonDoc from '../../assets/convert-extensions/json-doc.png';
import CsvDoc from '../../assets/convert-extensions/csv-doc.png';
import YamlDoc from '../../assets/convert-extensions/yaml-doc.png';
import XmlDoc from '../../assets/convert-extensions/xml-doc.png';

const Carousel: React.FC = () => {
    const arrOfImg = React.useMemo(() => [JsonDoc, CsvDoc, YamlDoc, XmlDoc], []);
    const [imageIndex, setImageIndex] = React.useState<IImageIndex>({ left: 0, right: 1 });

    const currentSide = React.useRef<Side>(Side.left);

    const getRandomInt = (min: number, max: number) => {
        min = Math.ceil(min);
        max = Math.floor(max);
        return Math.floor(Math.random() * (max - min)) + min;
    };

    const randNewImg = (obj: IImageIndex): IImageIndex => {
        let newIndex: number = obj[currentSide.current];
        const noUseSide: Side = currentSide.current === Side.left ? Side.right : Side.left;

        while (obj[currentSide.current] === newIndex || obj[noUseSide] === newIndex) {
            newIndex = getRandomInt(0, arrOfImg.length);
        }

        currentSide.current = noUseSide;
        return { ...obj, [noUseSide]: newIndex };
    };

    React.useEffect(() => {
        const interval = setInterval(() => {
            setImageIndex(randNewImg);
        }, 3000);

        return () => {
            clearInterval(interval);
        };
    }, [setImageIndex]);

    const isVisible = (imageIndex: number, currentIndex: number): string => {
        return imageIndex === currentIndex ? 'extension -visible' : 'extension';
    };

    return (
        <div className="carousel--wrapper">
            <img className={isVisible(0, imageIndex.left)} src={arrOfImg[0]} />
            <img className={isVisible(1, imageIndex.left)} src={arrOfImg[1]} />
            <img className={isVisible(2, imageIndex.left)} src={arrOfImg[2]} />
            <img className={isVisible(3, imageIndex.left)} src={arrOfImg[3]} />
            <i className="refresh fa fa-refresh" aria-hidden="true"></i>
            <img className={isVisible(0, imageIndex.right)} src={arrOfImg[0]} />
            <img className={isVisible(1, imageIndex.right)} src={arrOfImg[1]} />
            <img className={isVisible(2, imageIndex.right)} src={arrOfImg[2]} />
            <img className={isVisible(3, imageIndex.right)} src={arrOfImg[3]} />
        </div>
    );
};

export default Carousel;
