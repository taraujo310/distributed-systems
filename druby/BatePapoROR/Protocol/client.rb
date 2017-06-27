require 'drb'

class Messenger
  def initialize
    @server = DRbObject.new_with_uri('druby://localhost:61676')
    @server.define_strategy(:no_favoring)
    @file = "arq1"

    puts "Bem vindo ao Bate Papo ROR!"
    print "Para começar, escolha seu nickname: "

    @nickname = gets.strip
    @server.write(@file, "server > #{@nickname} entrou no chat")
    @messages = []
    @reader = get_reader
    @writer = get_writer
    @exited = false

    start
  end

  def get_reader
    Thread.new do
      loop do
        messages = @server.read(@file)

        break if @exited

        if @messages.size < messages.size
          @messages = messages
          print "\n#{messages.last.strip}\nSua mensagem: "
        end
      end
    end
  end

  def get_writer
    Thread.new do
      loop do
        message = gets.strip

        if message == "sair"
          @server.write(@file, "#{@nickname} saiu")
          @exited = true
          break
        else
          @server.write(@file, "#{@nickname} > #{message}")
        end
      end
    end
  end

  def start
    @reader.run
    @writer.run
    @writer.join
    @reader.join
  end
end

Messenger.new
