import React from 'react';

// components
import ExtensionListComponent from '../extension-list-component';

// hooks
import { useTranslation } from 'react-i18next';

// types
import { AllowFileType } from '../../core/types/enums';

// styles
import './styles.scss';

// assets
import JsonDoc from '../../assets/convert-extensions/json-doc.png';
import CsvDoc from '../../assets/convert-extensions/csv-doc.png';
import YamlDoc from '../../assets/convert-extensions/yaml-doc.png';
import XmlDoc from '../../assets/convert-extensions/xml-doc.png';

type Props = {
    file: File;
    setFile: React.Dispatch<File | null>;
};

const UploadPlaceWithFile: React.FC<Props> = (props: Props) => {
    const { file, setFile } = props;

    const { t } = useTranslation();

    const currentFile = React.useMemo((): React.ReactElement => {
        switch (file.type) {
            case AllowFileType.xml:
                return <img src={XmlDoc} className="single-extension" />;
            case AllowFileType.json:
                return <img src={JsonDoc} className="single-extension" />;
            case AllowFileType.csv:
                return <img src={CsvDoc} className="single-extension" />;
            default:
                return <img src={YamlDoc} className="single-extension" />;
        }
    }, [file]);

    const handleDeleteClick = (): void => {
        setFile(null);
    };

    const handleConvertClick = async (type: AllowFileType): Promise<void> => {
        console.log(type);
    };

    return (
        <div className="upload--container-ready">
            <i className="trash fa fa-trash-o" aria-hidden="true" onClick={handleDeleteClick} />
            {currentFile}
            <span>{file.name}</span>
            <span className="convert-msg">{t('common.convertTo')}:</span>
            <ExtensionListComponent handleClick={handleConvertClick} typesForExclude={[file.type]} />
        </div>
    );
};

export default UploadPlaceWithFile;
