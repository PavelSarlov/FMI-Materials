export interface FileFormat {
  [format: string]: {
    icon: string;
    name: string;
  };
}

export const FILE_FORMATS: FileFormat = {
  'text/plain': { icon: 'text_snippet', name: 'Text' },
  'text/html': { icon: 'html', name: 'HTML' },
  'text/css': { icon: 'css', name: 'CSS' },
  'application/javascript': { icon: 'javascript', name: 'JavaScript' },
  'application/x-httpd-php': { icon: 'php', name: 'PHP' },
  'image/png': { icon: 'image', name: 'PNG' },
  'application/vnd.openxmlformats-officedocument.wordprocessingml.document': {
    icon: 'description',
    name: 'Docs',
  },
  'application/pdf': { icon: 'picture_as_pdf', name: 'PDF' },
  'application/octet-stream': { icon: 'insert_drive_file', name: 'Binary' },
  default: { icon: 'text_snippet', name: 'Text' },
};
