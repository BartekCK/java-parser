import React from 'react';

// styles
import './styles.scss';

// types
import { AllowFileType } from '../../core/types/enums';

// assets
import JsonDoc from '../../assets/convert-extensions/json-doc.png';
import CsvDoc from '../../assets/convert-extensions/csv-doc.png';
import YamlDoc from '../../assets/convert-extensions/yaml-doc.png';
import XmlDoc from '../../assets/convert-extensions/xml-doc.png';

type Props = {
    handleClick?: (type: AllowFileType) => void | Promise<void>;
};

const ExtensionListComponent: React.FC<Props> = (props: Props) => {
    const { handleClick } = props;

    const onClick = async (type: AllowFileType): Promise<void> => {
        if (handleClick) {
            await handleClick(type);
        }
    };

    return (
        <div className="extensions">
            <img src={JsonDoc} className="single-extension" onClick={() => onClick(AllowFileType.json)} />
            <img src={CsvDoc} className="single-extension" onClick={() => onClick(AllowFileType.csv)} />
            <img src={YamlDoc} className="single-extension" onClick={() => onClick(AllowFileType.yaml)} />
            <img src={XmlDoc} className="single-extension" onClick={() => onClick(AllowFileType.xml)} />
        </div>
    );
};

export default ExtensionListComponent;
