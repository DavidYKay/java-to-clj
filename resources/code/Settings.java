app.setShowSettings(false);
AppSettings settings = new AppSettings(true);
settings.put("Width", 1280);
settings.put("Height", 720);
settings.put("Title", "My awesome Game");
settings.put("VSync", true);
settings.put("Samples", 4);
app.setSettings(settings);
app.start();
