public String API(String id){
        LambdaQueryWrapper<MyFodder> lqw = new LambdaQueryWrapper<>();
        lqw.eq(MyFodder::getId,id);
        MyFodder myFodder = myFodderDao.selectOne(lqw);
        String path = ImageUtil.deleteURLPath(myFodder.getSrc());
        String newPath = System.currentTimeMillis()+path;
        //使用模型进行背景去除
        String command = "backgroundremover -i \""+"***"+path+"\" -o \""+"***"+newPath+"\"";
        try {
            ProcessBuilder processBuilder = new ProcessBuilder();

            processBuilder.command("cmd.exe", "/c", command);

            Process process = processBuilder.start();

            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println("External command executed successfully.");
            } else {
                System.out.println("External command execution failed with exit code: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return "http://localhost:8085/img/"+newPath;
    }
