import {
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  Divider,
  IconButton,
} from '@material-ui/core';
import { Close } from '@material-ui/icons';

const HirenetModal = ({
  open,
  title,
  body: Body,
  onClose = null,
  onNegative,
  onAffirmative,
  affirmativeLabel,
  negativeLabel,
  affirmativeColor,
  negativeColor = 'black',
  showActionButtons = true,
}) => {
  return (
    <Dialog open={open} onClose={onClose ? onClose : onNegative}>
      <div className="p-2">
        <div className="flex justify-end">
          <IconButton
            onClick={onClose ? onClose : onNegative}
            style={{ width: 30, height: 30 }}
          >
            <Close />
          </IconButton>
        </div>
        <h2 className="font-semibold px-4 text-lg">{title}</h2>
      </div>
      <Divider />

      <DialogContent className="p-0">
        {<Body showDisclaimer={showActionButtons}></Body>}
        {/* {Body} */}
      </DialogContent>
      <Divider />
      {showActionButtons && (
        <DialogActions>
          <Button style={{ color: negativeColor }} onClick={onNegative}>
            {negativeLabel}
          </Button>
          {onAffirmative && (
            <Button
              style={{ color: affirmativeColor }}
              onClick={onAffirmative}
              autoFocus
            >
              {affirmativeLabel}
            </Button>
          )}
        </DialogActions>
      )}
    </Dialog>
  );
};

export const PlainTextBody = ({ children }) => (
  <div className="p-6 text-sm font-medium">{children}</div>
);

export default HirenetModal;
