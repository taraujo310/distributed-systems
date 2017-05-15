require_relative 'resource.rb'

class WLocker
  def initialize(filepath)
    @resource = initialize_resource(filepath)

    @readers_counter = 0
    @writers_counter = 0

    @readers_counter_mutex = Mutex.new # protects @readers_counter
		@writers_counter_mutex = Mutex.new # protects @writers_counter
    @readers_locker_mutex = Mutex.new # Protects readers locker
  	@close_to_writers = Mutex.new
  	@close_to_readers = Mutex.new
  end

  def access_read
    @readers_locker_mutex.synchronize do
      @close_to_readers.synchronize do
        @readers_counter_mutex.synchronize do
          @close_to_writers.lock if((@readers_counter += 1) == 1)
        end
      end
    end

    yield(@resource)

    @readers_counter_mutex.synchronize do
      @close_to_writers.unlock if((@readers_counter -= 1) == 0)
    end
  end

  def access_write
    @writers_counter_mutex.synchronize do
      @close_to_readers.lock if ((@writers_counter += 1) == 1) # impede a entrada de leitores na região crítica
    end

    @close_to_writers.synchronize do
      yield(@resource)
    end # impede escritores simultâneos na região crítica

    @writers_counter_mutex.synchronize do
      @close_to_readers.unlock if ((@writers_counter -= 1) == 0)
    end
  end

  private
  def initialize_resource(filepath="")
    raise NoNameForFileError, 'There is no file with empty name' unless filepath && !filepath.empty?
    Resource.new(filepath)
  end
end
