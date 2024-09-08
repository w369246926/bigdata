//package com.flink.io;
//
//@GetMapping("/export")
//public void exportDataToExcel(@RequestParam("downloadPath") String downloadPath) throws IOException {
//        // 从数据库中获取数据
//        List<User> userList = userService.getAllUsers();
//
//        // 创建 Excel 工作簿
//        Workbook workbook = new XSSFWorkbook();
//        Sheet sheet = workbook.createSheet("User Data");
//
//        // 创建表头
//        Row headerRow = sheet.createRow(0);
//        headerRow.createCell(0).setCellValue("ID");
//        headerRow.createCell(1).setCellValue("Name");
//        headerRow.createCell(2).setCellValue("Email");
//
//        // 填充数据
//        int rowNum = 1;
//        for (User user : userList) {
//        Row row = sheet.createRow(rowNum++);
//        row.createCell(0).setCellValue(user.getId());
//        row.createCell(1).setCellValue(user.getName());
//        row.createCell(2).setCellValue(user.getEmail());
//        }
//
//        // 将 Excel 文件写入磁盘
//        String filePath = "user_data.xlsx";
//        FileOutputStream outputStream = new FileOutputStream(filePath);
//        workbook.write(outputStream);
//        outputStream.close();
//
//        // 下载文件
//        File file = new File(filePath);
//        InputStream inputStream = new FileInputStream(file);
//        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
//        response.setContentType("application/vnd.ms-excel");
//        response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
//        OutputStream outputStream1 = response.getOutputStream();
//        IOUtils.copy(inputStream, outputStream1);
//        outputStream1.flush();
//        outputStream1.close();
//        inputStream.close();
//
//        // 删除临时文件
//        file.delete();
//        }
