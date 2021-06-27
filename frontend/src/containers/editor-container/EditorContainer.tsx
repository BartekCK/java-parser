import React from 'react';

// styles
import './styles.scss';
import Editor from '../../components/editor';
import { Parser } from '../../core/types/enums';
import { ApiInstance } from '../../api/config';
import { toast } from 'react-toastify';
import { useTranslation } from 'react-i18next';

const EditorContainer = React.forwardRef((props, divRef: React.RefObject<HTMLDivElement>) => {
    const [convertFrom, setConvertFrom] = React.useState<Parser>(Parser.json);
    const [convertTo, setConvertTo] = React.useState<Parser>(Parser.yaml);

    const [textBefore, setTextBefore] = React.useState<string>('');
    const [textAfter, setTextAfter] = React.useState<string>('');

    const { t } = useTranslation();

    const isDisabled: boolean = React.useMemo(() => {
        if(textBefore.length === 0){
            return true;
        }
        return convertFrom === convertTo
    }, [convertFrom, convertTo, textBefore]);

    const handleChange = (setterFunc) => (event): void => {
        setterFunc(Parser[event.target.value]);
    };

    const handleSubmit = async (): Promise<void> => {
        try {
            const { data } = await ApiInstance.post(`${convertFrom}/converter/${convertTo}`, textBefore, {
                headers: {
                    'Content-Type': 'text/plain',
                },
                responseType: convertTo === Parser.json ? 'json' : 'text',
            });

            setTextAfter(convertTo === Parser.json ? JSON.stringify(data) : data);
        } catch (e) {
            toast.error(t('message.somethingGoWrong'));
        }
    };

    return (
        <div ref={divRef} className="editor--wrapper">
            <Editor
                setText={setTextBefore}
                text={textBefore}
                onChangeType={handleChange(setConvertFrom)}
                selectValue={convertFrom}
            />
            <button className="btn btn-primary" onClick={handleSubmit} disabled={isDisabled}>
                <i className="refresh fa fa-refresh" aria-hidden="true" />
            </button>
            <Editor text={textAfter} onChangeType={handleChange(setConvertTo)} selectValue={convertTo} readonly />
        </div>
    );
});

export default EditorContainer;
