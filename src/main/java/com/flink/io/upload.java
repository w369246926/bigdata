//package com.flink.io;
//
//@PostMapping("/upload")
//public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
//        // 读取Excel文件
//        Workbook workbook = WorkbookFactory.create(file.getInputStream());
//        Sheet sheet = workbook.getSheetAt(0);
//        Iterator<Row> rowIterator = sheet.rowIterator();
//
//        // 解析Excel文件
//        List<YourObject> objects = new ArrayList<>();
//        while (rowIterator.hasNext()) {
//        Row row = rowIterator.next();
//        YourObject object = new YourObject();
//        object.setField1(row.getCell(0).getStringCellValue());
//        object.setField2(row.getCell(1).getNumericCellValue());
//        // ...
//        objects.add(object);
//        }
//
//        // 返回解析后的对象
//        return ResponseEntity.ok(objects);
//        }
