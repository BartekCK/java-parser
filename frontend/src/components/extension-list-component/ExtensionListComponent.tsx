import React from 'react';

// styles
import './styles.scss';

// types
import { AllowFileType } from '../../core/types/enums';
import { IRenderExtension } from './types';

// assets
import JsonDoc from '../../assets/convert-extensions/json-doc.png';
import CsvDoc from '../../assets/convert-extensions/csv-doc.png';
import YamlDoc from '../../assets/convert-extensions/yaml-doc.png';
import XmlDoc from '../../assets/convert-extensions/xml-doc.png';

type Props = {
    typesForExclude?: (AllowFileType | string)[];
    handleClick?: (type: AllowFileType) => void | Promise<void>;
};

const ExtensionListComponent: React.FC<Props> = (props: Props) => {
    const { handleClick, typesForExclude } = props;

    const onClick = async (type: AllowFileType): Promise<void> => {
        if (handleClick) {
            await handleClick(type);
        }
    };

    const elements: IRenderExtension[] = React.useMemo(
        (): IRenderExtension[] => [
            {
                type: AllowFileType.json,
                component: (
                    <img
                        key={AllowFileType.json}
                        src={JsonDoc}
                        className="single-extension"
                        onClick={() => onClick(AllowFileType.json)}
                    />
                ),
            },
            {
                type: AllowFileType.csv,
                component: (
                    <img
                        key={AllowFileType.csv}
                        src={CsvDoc}
                        className="single-extension"
                        onClick={() => onClick(AllowFileType.csv)}
                    />
                ),
            },
            {
                type: AllowFileType.yaml,
                component: (
                    <img
                        key={AllowFileType.yaml}
                        src={YamlDoc}
                        className="single-extension"
                        onClick={() => onClick(AllowFileType.yaml)}
                    />
                ),
            },
            {
                type: AllowFileType.xml,
                component: (
                    <img
                        key={AllowFileType.xml}
                        src={XmlDoc}
                        className="single-extension"
                        onClick={() => onClick(AllowFileType.xml)}
                    />
                ),
            },
        ],
        [typesForExclude, onClick],
    );

    const renderElements = (): React.ReactElement[] => {
        if (!typesForExclude) {
            return elements.map((el) => el.component);
        }
        return elements.filter((el) => !typesForExclude.includes(el.type)).map((el) => el.component);
    };

    return <div className="extensions">{renderElements()}</div>;
};

export default ExtensionListComponent;
