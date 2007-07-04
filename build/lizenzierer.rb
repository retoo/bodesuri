require "find"

class Lizenzierer
  @pfad
  @lizenztext
  
  def initialize(pfad, pfadZuLizenz) #Konstruktor
    @pfad = pfad
    
    # @lizenztext = File.read(@path) 
    
    
    File.open(pfadZuLizenz, 'r+') do |lizenzdatei|
      @lizenztext = lizenzdatei.readlines
    end
    puts @lizenztext
  end
  
  def lizenziere() #Fügt die Lizenz im Kopf jeder *.java Datei hinzu
    puts("Starten Lizenzierung aller Java-Dateien...")
    
    # Gehe den Verzeichnisbaum durch
    Find.find(@pfad) do |datei|
      next unless datei =~ /\.java$/    # wendet den RegExp an (=~)
      
      # *schuddr* 
      # verwend temporäre dateien in solchen fällen :) das ist viel schöner und im fehler fall geht dir
      # szeugs ned futsch
      
      File.open(datei, 'r+') do |f|
        zeilen = f.readlines
        f.pos = 0
        f.print @lizenztext
        f.print zeilen
        f.truncate(f.pos)
      end
      puts datei
    end
    
    puts("\n- - - - \nDie Java-Dateien wurden erfolgreich lizenziert!")
  end
end

lizenzierer = Lizenzierer.new("pfad_zum_source_verzeichnis", "pfad_zur_lizenz");
lizenzierer.lizenziere

# nice :)